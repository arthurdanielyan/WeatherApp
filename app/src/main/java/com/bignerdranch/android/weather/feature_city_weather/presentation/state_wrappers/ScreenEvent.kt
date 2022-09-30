package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

sealed interface ScreenEvent {
    class ShowSnackBar(val message: String) : ScreenEvent
}