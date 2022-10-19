package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers

import com.bignerdranch.android.weather.core.model.ShortForecastList

data class FiveDaysForecastState(
    val isLoading: Boolean = true,
    val list: ShortForecastList? = null,
    val error: String = ""
)