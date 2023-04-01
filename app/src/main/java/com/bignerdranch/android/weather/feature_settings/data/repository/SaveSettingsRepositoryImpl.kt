package com.bignerdranch.android.weather.feature_settings.data.repository

import android.content.SharedPreferences
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_IS_NOTIFICATION_ON
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_NOTIFICATION_TIME
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_SAVED_CITY
import com.bignerdranch.android.weather.core.constants.SHARED_PREF_TEMP_UNIT
import com.bignerdranch.android.weather.feature_settings.domain.repository.SaveSettingsRepository
import javax.inject.Inject

class SaveSettingsRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPreferences
) : SaveSettingsRepository {

    override suspend fun saveTempUnit(tempUnitKey: Int) {
        Units.selectedTempUnitInt = tempUnitKey
        sharedPref.edit().putInt(SHARED_PREF_TEMP_UNIT, tempUnitKey).apply()
    }

    override suspend fun saveTime(time: String) {
        SettingsStorage.notificationTime = time
        sharedPref.edit().putString(SHARED_PREF_NOTIFICATION_TIME, time).apply()
    }

    override suspend fun saveWeatherAlertOn(isOn: Boolean) {
        SettingsStorage.isWeatherAlertNotificationsEnabled = isOn
        sharedPref.edit().putBoolean(SHARED_PREF_IS_NOTIFICATION_ON, isOn).apply()
    }

    override suspend fun saveCity(cityId: Float) {
        SettingsStorage.homeCityId = cityId
        sharedPref.edit().putFloat(SHARED_PREF_SAVED_CITY, cityId).apply()
    }
}