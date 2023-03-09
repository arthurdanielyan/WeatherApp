package com.bignerdranch.android.weather.core.di

import com.bignerdranch.android.weather.core.data.repository.LoadSettingsRepositoryImpl
import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityWeatherRepository(
        settingsRepositoryImpl: LoadSettingsRepositoryImpl
    ): LoadSettingsRepository
}