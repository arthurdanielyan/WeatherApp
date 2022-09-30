package com.bignerdranch.android.weather.core

import android.util.Log

const val API_ERROR_MESSAGE = "An unexpected error occurred"
const val NO_INTERNET_MESSAGE = "Check your internet connection"

const val ARG_CITY = "passing_city_key"

const val LOAD_MY_CITIES_BY = 5

inline fun log(log: Any?) = Log.d("myLogs", log.toString())