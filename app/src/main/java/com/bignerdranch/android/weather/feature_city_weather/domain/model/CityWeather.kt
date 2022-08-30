package com.bignerdranch.android.weather.feature_city_weather.domain.model


data class CityWeather(
    val city: String,
    val country: String,
    val tempInCelsius: Double,
    val tempInFahrenheit: Double,
    val isDay: Boolean,
    val description: String,
    val iconLink: String,
    val pressure: Int
)