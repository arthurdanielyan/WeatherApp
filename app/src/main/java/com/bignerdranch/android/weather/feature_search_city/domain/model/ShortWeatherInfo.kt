package com.bignerdranch.android.weather.feature_search_city.domain.model

data class ShortWeatherInfo(
    val city: String,
    val country: String,
    val tempInCelsius: Double,
    val tempInFahrenheit: Double
)
