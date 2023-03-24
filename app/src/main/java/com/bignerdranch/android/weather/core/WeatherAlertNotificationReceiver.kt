package com.bignerdranch.android.weather.core

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bignerdranch.android.weather.R
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.WEATHER_ALERT_NOTIFICATION_REQUEST_CODE
import com.bignerdranch.android.weather.core.constants.planWeatherAlertNotification
import java.time.LocalTime


class WeatherAlertNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(context, "weather_alert")
            .setContentTitle("My Notification")
            .setContentText("This is a notification from my app.")
            .setSmallIcon(R.drawable.ic_foreground_notification)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
        PendingIntent.getBroadcast(context,
            WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent!!, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        planWeatherAlertNotification(
            context,
            LocalTime.of(
                SettingsStorage.notificationTime.substringBefore(':').toInt(),
                SettingsStorage.notificationTime.substringAfter(':').toInt()
            )
        )
    }
}