package com.bignerdranch.android.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bignerdranch.android.weather.core.presentation.Screen
import com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.CityWeatherScreen
import com.bignerdranch.android.weather.feature_search_city.presentation.search_city.SearchCityScreen
import com.bignerdranch.android.weather.ui.theme.WeatherTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val ARG_CITY = "city"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SearchCityScreen.route
                    ) {
                        composable(
                            route = Screen.SearchCityScreen.route
                        ) {
                            SearchCityScreen(navController = navController)
                        }
                        composable(
                            route = Screen.CityWeatherScreen.route + "/{$ARG_CITY}"
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