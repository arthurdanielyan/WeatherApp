package com.bignerdranch.android.weather.feature_search_city.presentation.state_wrappers

import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo

data class ShortWeatherInfoState (
    val isLoading: Boolean = false,
    val shortWeatherInfo: ShortWeatherInfo? = null,
    val error: String = ""
)