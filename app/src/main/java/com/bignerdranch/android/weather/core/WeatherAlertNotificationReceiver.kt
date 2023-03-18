package com.bignerdranch.android.weather.core

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.bignerdranch.android.weather.R
import com.bignerdranch.android.weather.core.constants.WEATHER_ALERT_NOTIFICATION_REQUEST_CODE
import com.bignerdranch.android.weather.core.constants.log


class WeatherAlertNotificationReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(context, "weather_alert")
            .setContentTitle("My Notification")
            .setContentText("This is a notification from my app.")
            .setSmallIcon(R.drawable.ic_foreground_notification)
            .setAutoCancel(true)
        notificationManager.notify(0, builder.build())
        val pendingIntent = PendingIntent.getBroadcast(context,
            WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent!!, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        log("notification sent")
    }
}