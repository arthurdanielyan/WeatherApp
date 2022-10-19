package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

import com.bignerdranch.android.weather.core.model.ShortForecastList

data class ShortForecastState(
    val isLoading: Boolean = true,
    val value: ShortForecastList? = null,
    val error: String = ""
)