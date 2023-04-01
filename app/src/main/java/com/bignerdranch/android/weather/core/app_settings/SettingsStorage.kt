package com.bignerdranch.android.weather.core.app_settings

import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity

object SettingsStorage {
    var isWeatherAlertNotificationsEnabled = false
    var homeCityId = -1f
//    var homeCity: MyCity? = null
    var notificationTime = "00:00"
}