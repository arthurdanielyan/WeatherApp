package com.bignerdranch.android.weather

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.bignerdranch.android.weather.feature_daily_notifications.WeatherAlertNotificationReceiver
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.NOTIFICATION_CITY_INFO_EXTRA
import com.bignerdranch.android.weather.core.constants.WEATHER_ALERT_NOTIFICATION_REQUEST_CODE
import com.bignerdranch.android.weather.core.domain.usecases.LoadSettingsUseCase
import com.bignerdranch.android.weather.feature_daily_notifications.domain.repository.DailyNotificationRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application() {

    // Injected here so that if the launcher screen changes, the injection doesn't need to be changed
    @Inject lateinit var loadSettingsUseCase: LoadSettingsUseCase
    @Inject lateinit var coroutineDispatcher: CoroutineDispatcher

    @Inject lateinit var dailyNotificationRepository: DailyNotificationRepository

    var settingsLoaded = false

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(coroutineDispatcher + SupervisorJob()).launch {
            loadSettingsUseCase()
            settingsLoaded = true
            if (SettingsStorage.isWeatherAlertNotificationsEnabled) {
                planWeatherAlertNotification(
                    LocalTime.of(
                        SettingsStorage.notificationTime.substringBefore(':').toInt(),
                        SettingsStorage.notificationTime.substringAfter(':').toInt()
                    )
                )
            } else {
                turnOffNotification()
            }
        }
    }

    fun planWeatherAlertNotification(time: LocalTime) {
        CoroutineScope(coroutineDispatcher + SupervisorJob()).launch {
            val notificationWeatherInfo =
                async(coroutineDispatcher) {
                    val myCity =
                        dailyNotificationRepository.getSavedCity()?.cityName ?: return@async null
                    val weatherInfo = dailyNotificationRepository.getNotificationWeather(myCity)
                    weatherInfo
                }.await()// ?: return@launch

            val intent = Intent(applicationContext, WeatherAlertNotificationReceiver::class.java).apply {
                action = "android.intent.action.ALARM"
                putExtra(NOTIFICATION_CITY_INFO_EXTRA, notificationWeatherInfo)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                WEATHER_ALERT_NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val triggerTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, time.hour)
                set(Calendar.MINUTE, time.minute)
                set(Calendar.SECOND, 0)
            }

            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


            if (System.currentTimeMillis() <= triggerTime.timeInMillis) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime.timeInMillis,
                    pendingIntent
                )
            } else {
                triggerTime.add(Calendar.DAY_OF_MONTH, 1)
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime.timeInMillis,
                    pendingIntent
                )
            }
        }
    }

    fun turnOffNotification() {
        val intent = Intent(applicationContext, WeatherAlertNotificationReceiver::class.java)
        intent.action = "android.intent.action.ALARM"
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()
    }
}