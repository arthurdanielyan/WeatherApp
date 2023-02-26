package com.bignerdranch.android.weather.feature_search_city.presentation.state_wrappers

import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo

data class MyCitiesState (
    val isLoading: Boolean = true,
    val myCities: List<ShortWeatherInfo>? = null,
    val error: String = ""
)