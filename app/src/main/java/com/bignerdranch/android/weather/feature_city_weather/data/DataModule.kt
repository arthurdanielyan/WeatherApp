package com.bignerdranch.android.weather.feature_city_weather.data

import com.bignerdranch.android.weather.feature_city_weather.data.repository.CityWeatherRepositoryImpl
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCityWeatherRepository(
        cityWeatherRepositoryImpl: CityWeatherRepositoryImpl
    ): CityWeatherRepository
}