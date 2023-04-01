package com.bignerdranch.android.weather.core.data.repository

import android.content.SharedPreferences
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.constants.*
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity
import javax.inject.Inject

class LoadSettingsRepositoryImpl @Inject constructor (
    private val sharedPreferences: SharedPreferences,
    private val myCitiesDao: MyCitiesDao
) : LoadSettingsRepository {

    override suspend fun loadSettings() {
        Units.selectedTempUnitInt = sharedPreferences.getInt(SHARED_PREF_TEMP_UNIT, Units.TempUnits.CELSIUS.key)
        SettingsStorage.notificationTime = sharedPreferences.getString(SHARED_PREF_NOTIFICATION_TIME, "00:00") ?: "00:00"
        SettingsStorage.isWeatherAlertNotificationsEnabled = sharedPreferences.getBoolean(SHARED_PREF_IS_NOTIFICATION_ON, true)
        SettingsStorage.homeCityId = sharedPreferences.getFloat(SHARED_PREF_SAVED_CITY, -1f)
        val city = myCitiesDao.getCity(SettingsStorage.homeCityId)
        if(city != null)
        SettingsStorage.homeCity = MyCity(city.city, city.id)
        else SettingsStorage.homeCity = null

        log(SettingsStorage.homeCity)
    }
}