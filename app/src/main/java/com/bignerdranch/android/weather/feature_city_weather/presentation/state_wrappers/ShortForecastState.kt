package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

import com.bignerdranch.android.weather.feature_city_weather.domain.model.ShortForecast

data class ShortForecastState(
    val isLoading: Boolean = true,
    val value: ShortForecast? = null,
    val error: String = ""
)