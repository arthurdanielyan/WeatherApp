package com.bignerdranch.android.weather.core

import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

const val API_ERROR_MESSAGE = "An unexpected error occurred"
const val NO_INTERNET_MESSAGE = "Check your internet connection"

const val ARG_CITY = "passing_city_key"

inline fun log(log: Any?) = Log.d("myLogs", log.toString())