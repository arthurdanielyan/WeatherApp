package com.bignerdranch.android.weather.feature_settings.domain.repository

import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity

interface SaveSettingsRepository {

    suspend fun saveTempUnit(tempUnitKey: Int)

    suspend fun saveTime(time: String)

    suspend fun saveWeatherAlertOn(isOn: Boolean)

    suspend fun saveCity(cityId: Float)
}