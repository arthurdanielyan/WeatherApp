package com.bignerdranch.android.weather.feature_daily_notifications.data.repository

import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.MyCity
import com.bignerdranch.android.weather.feature_daily_notifications.data.dto.toNotificationWeatherInfo
import com.bignerdranch.android.weather.feature_daily_notifications.domain.model.NotificationWeatherInfo
import com.bignerdranch.android.weather.feature_daily_notifications.domain.repository.DailyNotificationRepository
import java.io.IOException
import javax.inject.Inject

class DailyNotificationRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dao: MyCitiesDao
)  : DailyNotificationRepository {

    override suspend fun getNotificationWeather(city: String): NotificationWeatherInfo? =
        try {
            weatherApi.getNotificationWeather(city).toNotificationWeatherInfo()
        } catch (e: IOException) {
            null
        }

    override suspend fun getSavedCity(): MyCity? {
        val city = dao.getCity(SettingsStorage.homeCityId) ?: return null
        return MyCity(
            cityName = city.city,
            id = city.id
        )
    }
}