package com.bignerdranch.android.weather.feature_search_city.domain.repository

import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import kotlinx.coroutines.flow.Flow

interface SearchCityRepository {

    suspend fun searchCity(cityQuery: String): Flow<Result<List<ShortWeatherInfo>>>

    suspend fun getCityWeather(city: String): Flow<Result<ShortWeatherInfo>>

    suspend fun getCities(): Flow<List<ShortWeatherInfo>>
}