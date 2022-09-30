package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import android.graphics.Bitmap
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetIconUseCase(
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Bitmap =
        withContext(coroutineDispatcher) {
            cityWeatherRepository.getIcon()
        }

}