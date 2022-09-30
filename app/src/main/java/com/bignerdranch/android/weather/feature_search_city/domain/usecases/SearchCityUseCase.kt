package com.bignerdranch.android.weather.feature_search_city.domain.usecases

import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchCityUseCase(
    private val repository: SearchCityRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cityName: String) =
        withContext(coroutineDispatcher) {
            repository.searchCity(cityName)
        }
}