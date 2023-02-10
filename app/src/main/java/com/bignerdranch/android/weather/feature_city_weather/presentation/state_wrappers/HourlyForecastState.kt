package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast


data class HourlyForecastState (
    val isLoading: Boolean = true,
    val list: List<HourForecast>? = null,
    val error: String = ""
)