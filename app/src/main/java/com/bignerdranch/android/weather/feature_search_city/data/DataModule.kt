package com.bignerdranch.android.weather.feature_search_city.data

import com.bignerdranch.android.weather.feature_search_city.data.repository.SearchCityRepositoryImpl
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import org.koin.dsl.module

val dataModule = module {
    single<SearchCityRepository> {
        SearchCityRepositoryImpl(get())
    }
}