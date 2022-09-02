package com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.state_wrappers

import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather

data class CityWeatherState (
    val isLoading: Boolean = true,
    val cityWeather: CityWeather? = null,
    val error: String = ""
)