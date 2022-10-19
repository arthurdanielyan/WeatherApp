package com.bignerdranch.android.weather.feature_5_days_forecast.data

import com.bignerdranch.android.weather.feature_5_days_forecast.data.repository.FiveDaysForecastRepositoryImpl
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
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
    abstract fun bindFiveDaysForecastRepository(
        fiveDaysForecastRepositoryImpl: FiveDaysForecastRepositoryImpl
    ): FiveDaysForecastRepository
}