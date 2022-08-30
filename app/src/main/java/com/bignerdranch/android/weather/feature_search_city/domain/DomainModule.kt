package com.bignerdranch.android.weather.feature_search_city.domain

import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        SearchCityUseCase(get())
    }
}