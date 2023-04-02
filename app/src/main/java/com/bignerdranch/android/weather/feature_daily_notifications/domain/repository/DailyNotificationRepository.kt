package com.bignerdranch.android.weather.feature_daily_notifications.domain.repository

import com.bignerdranch.android.weather.feature_daily_notifications.domain.model.NotificationWeatherInfo
import com.bignerdranch.android.weather.core.model.MyCity

interface DailyNotificationRepository {

    suspend fun getNotificationWeather(city: String): NotificationWeatherInfo?

    suspend fun getSavedCity(): MyCity?
}