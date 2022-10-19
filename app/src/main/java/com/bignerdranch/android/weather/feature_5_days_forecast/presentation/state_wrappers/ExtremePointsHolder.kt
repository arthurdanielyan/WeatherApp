package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers

data class ExtremePointsHolder(
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double
) {

    companion object {
        val demoExtremePoints = listOf(
            ExtremePointsHolder(23.0, 10.0),
            ExtremePointsHolder(20.0, 13.0),
            ExtremePointsHolder(24.0, 15.0),
            ExtremePointsHolder(18.0, 9.0),
            ExtremePointsHolder(18.0, 12.0),
        )
    }
}