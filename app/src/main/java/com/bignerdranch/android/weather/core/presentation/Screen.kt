package com.bignerdranch.android.weather.core.presentation

//sealed class Screen(val route: String) {
//    object SearchCityScreen : Screen("search_city_screen")
//    object CityWeatherScreen : Screen("city_search_screen")
//}

enum class Screen(val route: String) {
    SearchCityScreen("search_city_screen"),
    CityWeatherScreen("city_search_screen"),
    FiveDaysForecast("five_days_forecast_screen")
}
