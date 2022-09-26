package com.bignerdranch.android.weather.feature_search_city.domain.usecases

import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository

class SearchCityUseCase(
    private val repository: SearchCityRepository
) {

    suspend operator fun invoke(cityName: String) = repository.searchCity(cityName)
}