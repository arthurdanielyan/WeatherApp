package com.bignerdranch.android.weather.feature_search_city.domain.usecases

import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.bignerdranch.android.weather.core.model.Result

class SearchCityUseCase @Inject constructor (
    private val repository: SearchCityRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cityName: String): Flow<Result<List<ShortWeatherInfo>>> =
        withContext(coroutineDispatcher) {
            repository.searchCity(cityName)
        }
}