package com.bignerdranch.android.weather.core.model

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible

data class WeatherInfo(
    var date: Date,
    var dayName: String,
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double,
    val maxTempInFahrenheit: Double,
    val minTempInFahrenheit: Double,
    val description: String,
    val iconUrl: String,
    val icon: Bitmap? = null
) {
    fun getMaxTempString(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                maxTempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                maxTempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitName}"

    fun getMinTempString(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                minTempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                minTempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitName}"

    fun getMaxTemp(): Double =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                maxTempInCelsius
            }
            Units.TempUnits.FAHRENHEIT -> {
                maxTempInFahrenheit
            }
        }

    fun getMinTemp(): Double =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                minTempInCelsius
            }
            Units.TempUnits.FAHRENHEIT -> {
                minTempInFahrenheit
            }
        }
}
