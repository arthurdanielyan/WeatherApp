package com.bignerdranch.android.weather.core.presentation

import com.bignerdranch.android.weather.core.ARG_CITY


enum class Screen(val route: String) {
    SearchCityScreen("search_city_screen"),
    CityWeatherScreen("city_weather_screen") {
        override val argumentedRoute = "$route/{$ARG_CITY}"
    },
    FiveDaysForecast("five_days_forecast_screen") {
        override val argumentedRoute = "$route/{$ARG_CITY}"
    };

    open val argumentedRoute: String = route
}
