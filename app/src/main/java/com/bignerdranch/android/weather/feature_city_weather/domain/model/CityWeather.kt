package com.bignerdranch.android.weather.feature_city_weather.domain.model

import android.graphics.Bitmap
import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo

fun CityWeather.toShortWeatherInfo() =
    ShortWeatherInfo(
        city, country, tempInCelsius, tempInFahrenheit
    )

data class CityWeather(
    val city: String,
    val country: String,
    val tempInCelsius: Double,
    val tempInFahrenheit: Double,
    val isDay: Boolean,
    val description: String,
    val iconUrl: String,
    var icon: Bitmap?,
    val pressure: Int
)