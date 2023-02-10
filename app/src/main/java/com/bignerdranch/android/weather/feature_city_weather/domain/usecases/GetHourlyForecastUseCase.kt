package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetHourlyForecastUseCase(
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): Flow<Result<List<HourForecast>>> =
        withContext(coroutineDispatcher) {
            cityWeatherRepository.getHourlyForecast(city)
        }
}