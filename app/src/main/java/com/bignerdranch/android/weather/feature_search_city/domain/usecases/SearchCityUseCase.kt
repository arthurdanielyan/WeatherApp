package com.bignerdranch.android.weather.feature_search_city.domain.usecases

import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository

class SearchCityUseCase(
    private val repository: SearchCityRepository
) {


    fun finalize() {
        log("SearchCityUseCase finalize")
    }

    suspend operator fun invoke(cityName: String) = repository.searchCity(cityName)
}