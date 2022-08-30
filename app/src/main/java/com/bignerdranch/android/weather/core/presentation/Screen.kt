package com.bignerdranch.android.weather.core.presentation

sealed class Screen(val route: String) {
    object SearchCityScreen : Screen("search_city_screen")
    object CityWeatherScreen : Screen("city_search_screen")
}
