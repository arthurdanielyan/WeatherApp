package com.bignerdranch.android.weather.core.domain.repository

interface LoadSettingsRepository {

    suspend fun loadSettings()
}
