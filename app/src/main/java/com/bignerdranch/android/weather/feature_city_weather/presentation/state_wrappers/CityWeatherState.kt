package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather

data class CityWeatherState (
    val isLoading: Boolean = true,
    val value: CityWeather? = null,
    val error: String = ""
)