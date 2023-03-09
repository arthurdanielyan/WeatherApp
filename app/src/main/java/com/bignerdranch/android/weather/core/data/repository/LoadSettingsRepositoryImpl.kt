package com.bignerdranch.android.weather.core.data.repository

import android.content.SharedPreferences
import com.bignerdranch.android.weather.core.app_settings.TEMP_UNIT
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import javax.inject.Inject

class LoadSettingsRepositoryImpl @Inject constructor (
    private val sharedPreferences: SharedPreferences
) : LoadSettingsRepository {

    override suspend fun loadSettings() {
        Units.selectedTempUnitInt = sharedPreferences.getInt(TEMP_UNIT, Units.TempUnits.CELSIUS.key)
    }
}