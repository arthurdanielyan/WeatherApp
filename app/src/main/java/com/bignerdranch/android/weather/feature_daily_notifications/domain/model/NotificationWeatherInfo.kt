package com.bignerdranch.android.weather.feature_daily_notifications.domain.model

import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import java.io.Serializable

data class NotificationWeatherInfo(
    val cityName: String,
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double,
    val maxTempInFahrenheit: Double,
    val minTempInFahrenheit: Double,
    val condition: String
) : Serializable {

    fun getMaxTemp(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                maxTempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                maxTempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitSign}"

    fun getMinTemp(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                minTempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                minTempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitSign}"
}
