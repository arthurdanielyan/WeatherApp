package com.bignerdranch.android.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.bignerdranch.android.weather.core.presentation.*
import com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.CityWeatherScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.search_city.SearchCityScreen
import com.bignerdranch.android.weather.ui.theme.WeatherTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    companion object {
        const val ARG_CITY = "city"
    }


    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            SearchCityScreen(navController = navController)
                        }

                        composable(
                            route = Screen.CityWeatherScreen.route + "/{$ARG_CITY}",
                            enterTransition = { slideInLeft },
                            exitTransition = { slideOutRight }
                        ) { navBackStackEntry: NavBackStackEntry ->
                            CityWeatherScreen(
                                city = navBackStackEntry.arguments?.getString(ARG_CITY) ?: "unexpected error"
                            )
                        }
                    }
                }
            }
        }
    }
}