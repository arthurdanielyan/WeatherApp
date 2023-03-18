package com.bignerdranch.android.weather.feature_settings.model.repository

interface SaveSettingsRepository {

    suspend fun saveTempUnit(tempUnitKey: Int)

    suspend fun saveTime(time: String)

    suspend fun saveWeatherAlertOn(isOn: Boolean)
}