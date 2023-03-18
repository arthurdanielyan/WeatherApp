package com.bignerdranch.android.weather.feature_city_weather.domain.model

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo

fun CityWeather.toShortWeatherInfo() =
    ShortWeatherInfo(
        city, country, tempInCelsius, tempInFahrenheit, lat, lon
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
    val pressure: Int,
    val lat: Double,
    val lon: Double
) {
    fun getTemp(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                tempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                tempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitName}"
}