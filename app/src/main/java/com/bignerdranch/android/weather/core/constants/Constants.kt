package com.bignerdranch.android.weather.core.constants

import android.util.Log

const val API_ERROR_MESSAGE = "An unexpected error occurred"
const val NO_INTERNET_MESSAGE = "Check your internet connection"

const val NOTIFICATION_CITY_INFO_EXTRA = "key_for_notification_intent"
const val WEATHER_ALERT_NOTIFICATION_REQUEST_CODE = 2

const val ARG_CITY = "passing_city_key"

inline fun log(log: Any?) = Log.d("myLogs", log.toString())