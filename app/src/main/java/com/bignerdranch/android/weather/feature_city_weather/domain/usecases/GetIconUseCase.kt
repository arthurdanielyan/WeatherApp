package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository

class GetIconUseCase(
    private val cityWeatherRepository: CityWeatherRepository
) {

    suspend operator fun invoke() = cityWeatherRepository.getIcon()
}