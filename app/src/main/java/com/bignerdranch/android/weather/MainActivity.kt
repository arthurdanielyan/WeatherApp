package com.bignerdranch.android.weather

import android.app.*
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.planWeatherAlertNotification
import com.bignerdranch.android.weather.core.presentation.*
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.FiveDaysForecastScreen
import com.bignerdranch.android.weather.feature_city_weather.presentation.CityWeatherScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.SearchCityScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.SearchCityViewModel
import com.bignerdranch.android.weather.feature_settings.presentation.SettingsScreen
import com.bignerdranch.android.weather.ui.theme.WeatherTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalTime
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Weather alerts"
            val channel = NotificationChannel("weather_alert", name, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Receive daily alerts about weather conditions"
            val notificationManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        planWeatherAlertNotification(this as Context,
            LocalTime.of(
                SettingsStorage.notificationTime.substringBefore(':').toInt(),
                SettingsStorage.notificationTime.substringAfter(':').toInt()
            ))

        val searchCityViewModel by viewModels<SearchCityViewModel>()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                searchCityViewModel.showSplashScreen
            }
        }

        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.SearchCityScreen.argumentedRoute
                    ) {
                        composable(
                            route = Screen.SearchCityScreen.argumentedRoute,
                            exitTransition = { slideOutLeft },
                            enterTransition = { slideInRight }
                        ) {
                            SearchCityScreen(navController = navController, searchCityViewModel)
                        }

                        composable(
                            route = Screen.CityWeatherScreen.argumentedRoute,
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight },
                        ) {
                            CityWeatherScreen(hiltViewModel(), navController)
                        }

                        composable(
                            route = Screen.FiveDaysForecast.argumentedRoute,
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight }
                        ) {
                            FiveDaysForecastScreen(hiltViewModel(), navController)
                        }

                        composable(
                            route = Screen.SettingsScreen.argumentedRoute,
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight }
                        ) {
                            SettingsScreen(hiltViewModel(), navController)
                        }
                    }
                }
            }
        }
    }
}