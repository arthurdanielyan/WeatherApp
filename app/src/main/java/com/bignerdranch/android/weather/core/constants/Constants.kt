package com.bignerdranch.android.weather.core.constants

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bignerdranch.android.weather.core.WeatherAlertNotificationReceiver
import java.time.LocalTime
import java.util.*

const val API_ERROR_MESSAGE = "An unexpected error occurred"
const val NO_INTERNET_MESSAGE = "Check your internet connection"

const val WEATHER_ALERT_NOTIFICATION_REQUEST_CODE = 2

const val ARG_CITY = "passing_city_key"

inline fun log(log: Any?) = Log.d("myLogs", log.toString())


fun planWeatherAlertNotification(context: Context, time: LocalTime) {
    val intent = Intent(context, WeatherAlertNotificationReceiver::class.java)
    intent.action = "android.intent.action.ALARM"
    val pendingIntent = PendingIntent.getBroadcast(context, WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val triggerTime = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, time.hour)
        set(Calendar.MINUTE, time.minute)
        set(Calendar.SECOND, 0)
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    if(System.currentTimeMillis() <= triggerTime.timeInMillis) {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, pendingIntent)
    } else {
        triggerTime.add(Calendar.DAY_OF_MONTH, 1)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime.timeInMillis, pendingIntent)
    }
}

fun turnOffNotification(context: Context) {
    val intent = Intent(context, WeatherAlertNotificationReceiver::class.java)
    intent.action = "android.intent.action.ALARM"
    val pendingIntent = PendingIntent.getBroadcast(context, WEATHER_ALERT_NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    pendingIntent.cancel()
}