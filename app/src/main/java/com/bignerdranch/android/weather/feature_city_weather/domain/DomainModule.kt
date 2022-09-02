package com.bignerdranch.android.weather.feature_city_weather.domain

import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetIconUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        GetCityWeatherUseCase(get())
    }

    single {
        GetIconUseCase(get())
    }
}