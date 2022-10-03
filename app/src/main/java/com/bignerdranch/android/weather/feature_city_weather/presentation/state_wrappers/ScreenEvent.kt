package com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers

sealed interface ScreenEvent {
    object Nothing : ScreenEvent
    class ShowToast(val message: String) : ScreenEvent
}