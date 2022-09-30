package com.bignerdranch.android.weather.feature_city_weather.domain

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.Get3DaysForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.SaveCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCityWeatherUseCase(cityWeatherRepository: CityWeatherRepository) =
        GetCityWeatherUseCase(cityWeatherRepository, Dispatchers.Default)


    @Provides
    @Singleton
    fun provideGetIconUseCase(cityWeatherRepository: CityWeatherRepository) =
        GetIconUseCase(cityWeatherRepository, Dispatchers.Default)

    @Provides
    @Singleton
    fun provideGet3DaysForecastUseCase(cityWeatherRepository: CityWeatherRepository) =
        Get3DaysForecastUseCase(cityWeatherRepository, Dispatchers.Default)

    @Provides
    @Singleton
    fun provideSaveCityUseCase(cityWeatherRepository: CityWeatherRepository) =
        SaveCityUseCase(cityWeatherRepository, Dispatchers.Default)
}