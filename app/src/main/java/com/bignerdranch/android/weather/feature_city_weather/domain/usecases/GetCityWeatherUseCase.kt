package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCityWeatherUseCase(
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {


    suspend operator fun invoke(city: String) =
        withContext(coroutineDispatcher) {
            cityWeatherRepository.getWeather(city)
        }
}