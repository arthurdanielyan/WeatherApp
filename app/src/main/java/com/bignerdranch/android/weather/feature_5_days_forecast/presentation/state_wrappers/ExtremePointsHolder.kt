package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers

data class ExtremePointsHolder(
    val maxCelsius: Int,
    val minCelsius: Int
) {

    companion object {
        val demoExtremePoints = listOf(
            ExtremePointsHolder(23, 10),
            ExtremePointsHolder(20, 13),
            ExtremePointsHolder(24, 15),
            ExtremePointsHolder(18, 9),
            ExtremePointsHolder(18, 12),
        )
    }
}