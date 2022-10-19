package com.bignerdranch.android.weather.core.model

import android.graphics.Bitmap

data class ForecastDay(
    var date: String,
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double,
    val maxTempInFahrenheit: Double,
    val minTempInFahrenheit: Double,
    val description: String,
    val icon: Bitmap? = null
)