package com.bignerdranch.android.weather.feature_search_city.domain.repository

import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {

    suspend fun searchCity(cityName: String): Flow<Result<ShortWeatherInfo>>

    suspend fun getCities(): Flow<List<ShortWeatherInfo>>
}