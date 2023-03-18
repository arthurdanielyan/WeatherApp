package com.bignerdranch.android.weather.core.data.repository

import android.content.SharedPreferences
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_TEMP_UNIT
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_IS_NOTIFICATION_ON
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_NOTIFICATION_TIME
import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import javax.inject.Inject

class LoadSettingsRepositoryImpl @Inject constructor (
    private val sharedPreferences: SharedPreferences
) : LoadSettingsRepository {

    override suspend fun loadSettings() {
        Units.selectedTempUnitInt = sharedPreferences.getInt(SHARED_PREF_TEMP_UNIT, Units.TempUnits.CELSIUS.key)
        SettingsStorage.notificationTime = sharedPreferences.getString(SHARED_PREF_NOTIFICATION_TIME, "00:00") ?: "00:00"
        SettingsStorage.isWeatherAlertNotificationsEnabled = sharedPreferences.getBoolean(SHARED_PREF_IS_NOTIFICATION_ON, true)
    }
}