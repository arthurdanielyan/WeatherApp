package com.bignerdranch.android.weather.feature_city_weather.presentation

import com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.CityWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        CityWeatherViewModel(get())
    }
}