package com.bignerdranch.android.weather.feature_search_city.data

import com.bignerdranch.android.weather.feature_search_city.data.repository.SearchCityRepositoryImpl
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//val dataModule = module {
//    single<SearchCityRepository> {
//        SearchCityRepositoryImpl(get())
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCityWeatherRepository(
        searchCityRepositoryImpl: SearchCityRepositoryImpl
    ): SearchCityRepository
}