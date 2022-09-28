package com.bignerdranch.android.weather.feature_city_weather.domain

import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.Get3DaysForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetIconUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//val domainModule = module {
//    single {
//        GetCityWeatherUseCase(get())
//    }
//
//    single {
//        GetIconUseCase(get())
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCityWeatherUseCase(cityWeatherRepository: CityWeatherRepository) =
        GetCityWeatherUseCase(cityWeatherRepository)


    @Provides
    @Singleton
    fun provideGetIconUseCase(cityWeatherRepository: CityWeatherRepository) =
        GetIconUseCase(cityWeatherRepository)

    @Provides
    @Singleton
    fun provideGet3DaysForecastUseCase(cityWeatherRepository: CityWeatherRepository) =
        Get3DaysForecastUseCase(cityWeatherRepository)
}