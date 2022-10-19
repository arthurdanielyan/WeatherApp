package com.bignerdranch.android.weather.core.presentation


enum class Screen(val route: String) {
    SearchCityScreen("search_city_screen"),
    CityWeatherScreen("city_search_screen"),
    FiveDaysForecast("five_days_forecast_screen")
}
