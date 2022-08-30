package com.bignerdranch.android.weather.core

import android.util.Log

const val API_ERROR_MESSAGE = "An unexpected error occurred"
const val NO_INTERNET_MESSAGE = "Check your internet connection"

inline fun log(log: Any?) = Log.d("myLogs", log.toString())