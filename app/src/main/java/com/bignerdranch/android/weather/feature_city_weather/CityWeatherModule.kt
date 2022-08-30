package com.bignerdranch.android.weather.feature_city_weather

import com.bignerdranch.android.weather.feature_city_weather.data.dataModule
import com.bignerdranch.android.weather.feature_city_weather.domain.domainModule
import com.bignerdranch.android.weather.feature_city_weather.presentation.presentationModule

val cityWeatherModule = dataModule + domainModule + presentationModule