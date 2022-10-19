package com.bignerdranch.android.weather.feature_5_days_forecast.domain

import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases.GetFiveDayForecastUseCase
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
    fun provideGetFiveDaysForecastUsecase(repository: FiveDaysForecastRepository): GetFiveDayForecastUseCase =
        GetFiveDayForecastUseCase(repository, Dispatchers.Default)
}