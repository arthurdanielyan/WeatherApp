package com.bignerdranch.android.weather.feature_city_weather.domain

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.Get3DaysForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.SaveCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetCityWeatherUseCase(cityWeatherRepository: CityWeatherRepository) =
        GetCityWeatherUseCase(cityWeatherRepository, Dispatchers.Default)
    
    @Provides
    @ViewModelScoped
    fun provideSaveCityUseCase(cityWeatherRepository: CityWeatherRepository) =
        SaveCityUseCase(cityWeatherRepository, Dispatchers.Default)

    @Provides
    @ViewModelScoped
    fun provideGet3DaysForecastUseCase(cityWeatherRepository: CityWeatherRepository) =
        Get3DaysForecastUseCase(cityWeatherRepository, Dispatchers.Default)
}