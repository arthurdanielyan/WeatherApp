package com.bignerdranch.android.weather.feature_city_weather.domain.model

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.model.Date

data class HourForecast(
    val time: String,
    val date: Date,
    val tempInCelsius: Double,
    val tempInFahrenheit: Double,
    val iconUrl: String,
    val icon: Bitmap?
)
