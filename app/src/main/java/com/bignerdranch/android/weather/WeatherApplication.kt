package com.bignerdranch.android.weather

import android.app.Application
import com.bignerdranch.android.weather.core.coreModule
import com.bignerdranch.android.weather.feature_city_weather.cityWeatherModule
import dagger.hilt.android.HiltAndroidApp
//import com.bignerdranch.android.weather.feature_search_city.searchCityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(coreModule /*+ searchCityModule*/ + cityWeatherModule)
        }
    }
}