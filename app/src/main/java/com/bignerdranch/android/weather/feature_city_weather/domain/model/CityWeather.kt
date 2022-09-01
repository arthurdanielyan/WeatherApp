package com.bignerdranch.android.weather.feature_city_weather.domain.model

import android.graphics.Bitmap


data class CityWeather(
    val city: String,
    val country: String,
    val tempInCelsius: Double,
    val tempInFahrenheit: Double,
    val isDay: Boolean,
    val description: String,
    var icon: Bitmap?,
    val pressure: Int
)