package com.bignerdranch.android.weather.feature_city_weather.data

import com.bignerdranch.android.weather.feature_city_weather.data.repository.CityWeatherRepositoryImpl
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<CityWeatherRepository> {
        CityWeatherRepositoryImpl(get())
    }
}