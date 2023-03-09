package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCityUseCase @Inject constructor (
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(cityWeather: ShortWeatherInfo) {
        withContext(coroutineDispatcher) {
            cityWeatherRepository.saveCity(cityWeather)
        }
    }
}