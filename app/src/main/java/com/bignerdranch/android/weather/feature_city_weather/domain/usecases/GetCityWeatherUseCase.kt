package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository

class GetCityWeatherUseCase(
    private val cityWeatherRepository: CityWeatherRepository
) {

    suspend operator fun invoke(city: String) = cityWeatherRepository.getWeather(city)
}