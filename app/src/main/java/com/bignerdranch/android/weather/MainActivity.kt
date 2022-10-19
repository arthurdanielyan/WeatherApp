package com.bignerdranch.android.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.bignerdranch.android.weather.core.ARG_CITY
import com.bignerdranch.android.weather.core.presentation.*
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.FiveDaysForecastScreen
import com.bignerdranch.android.weather.feature_city_weather.presentation.CityWeatherScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.SearchCityScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.SearchCityViewModel
import com.bignerdranch.android.weather.ui.theme.WeatherTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val searchCityViewModel by viewModels<SearchCityViewModel>()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                searchCityViewModel.myCities.value.isLoading
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
                        startDestination = Screen.SearchCityScreen.route
                    ) {
                        composable(
                            route = Screen.SearchCityScreen.route,
                            exitTransition = { slideOutLeft },
                            enterTransition = { slideInRight }
                        ) {
                            SearchCityScreen(navController = navController, searchCityViewModel)
                        }

                        composable(
                            route = Screen.CityWeatherScreen.route + "/{$ARG_CITY}",
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight },
                        ) {
                            CityWeatherScreen(hiltViewModel(), navController)
                        }

                        composable(
                            route = Screen.FiveDaysForecast.route + "/{$ARG_CITY}",
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight }
                        ) {
                            FiveDaysForecastScreen(hiltViewModel())
                        }
                    }
                }
            }
        }
    }
}