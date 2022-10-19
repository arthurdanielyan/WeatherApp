package com.bignerdranch.android.weather.feature_5_days_forecast.data

import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.feature_5_days_forecast.data.repository.FiveDaysForecastRepositoryImpl
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFiveDaysForecastRepository(weatherApi: WeatherApi): FiveDaysForecastRepository =
        FiveDaysForecastRepositoryImpl(weatherApi)
}