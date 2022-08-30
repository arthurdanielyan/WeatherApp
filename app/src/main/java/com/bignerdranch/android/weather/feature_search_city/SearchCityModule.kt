package com.bignerdranch.android.weather.feature_search_city

import com.bignerdranch.android.weather.feature_search_city.data.dataModule
import com.bignerdranch.android.weather.feature_search_city.domain.domainModule
import com.bignerdranch.android.weather.feature_search_city.presentation.presentationModule

val searchCityModule = dataModule + domainModule + presentationModule