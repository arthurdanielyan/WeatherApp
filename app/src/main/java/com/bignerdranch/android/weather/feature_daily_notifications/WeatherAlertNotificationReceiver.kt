package com.bignerdranch.android.weather.feature_daily_notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bignerdranch.android.weather.R
import com.bignerdranch.android.weather.WeatherApplication
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.NOTIFICATION_CITY_INFO_EXTRA
import com.bignerdranch.android.weather.core.constants.WEATHER_ALERT_NOTIFICATION_REQUEST_CODE
import com.bignerdranch.android.weather.core.constants.log
import com.bignerdranch.android.weather.feature_daily_notifications.domain.model.NotificationWeatherInfo
import java.time.LocalTime


class WeatherAlertNotificationReceiver : BroadcastReceiver() {

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {
        var weatherInfo: NotificationWeatherInfo? = null
        val weatherInfoSerializable =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getSerializableExtra(
                    NOTIFICATION_CITY_INFO_EXTRA,
                    NotificationWeatherInfo::class.java
                )
            } else {
                intent?.getSerializableExtra(NOTIFICATION_CITY_INFO_EXTRA)
            }
        if(weatherInfoSerializable != null) weatherInfo = weatherInfoSerializable as NotificationWeatherInfo


        log(weatherInfo)

        if (weatherInfo != null) {
            val notificationManager =
                context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(context, "weather_alert")
                .setContentTitle("Weather in ${weatherInfo.cityName} today")
                .setContentText("${weatherInfo.getMinTemp()}/${weatherInfo.getMaxTemp()}")
                .setContentText(weatherInfo.condition)
                .setSmallIcon(R.drawable.ic_foreground_notification)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "${weatherInfo.getMinTemp()}/${weatherInfo.getMaxTemp()}\n" +
                                weatherInfo.condition
                    )
                )
            notificationManager.notify(0, builder.build())
        }

        PendingIntent.getBroadcast(context,
            WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent!!, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        (context!!.applicationContext as WeatherApplication).planWeatherAlertNotification(
            LocalTime.of(
                SettingsStorage.notificationTime.substringBefore(':').toInt(),
                SettingsStorage.notificationTime.substringAfter(':').toInt()
            )
        )
    }
}