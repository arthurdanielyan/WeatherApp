package com.bignerdranch.android.weather.feature_search_city.presentation.state_wrappers

import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo

data class MyCitiesState (
    val isLoading: Boolean = true,
    val myCities: List<ShortWeatherInfo> = emptyList(),
    val error: String = ""
)