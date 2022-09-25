package com.bignerdranch.android.weather.feature_search_city.data

import com.bignerdranch.android.weather.core.data.WeatherApi
import com.bignerdranch.android.weather.feature_search_city.data.repository.SearchCityRepositoryImpl
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

//val dataModule = module {
//    single<SearchCityRepository> {
//        SearchCityRepositoryImpl(get())
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSearchCityRepository(weatherApi: WeatherApi): SearchCityRepository =
        SearchCityRepositoryImpl(weatherApi)
}