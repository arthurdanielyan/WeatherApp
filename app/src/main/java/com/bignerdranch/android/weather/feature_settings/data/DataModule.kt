package com.bignerdranch.android.weather.feature_settings.data

import com.bignerdranch.android.weather.feature_settings.data.SaveSettingsRepositoryImpl
import com.bignerdranch.android.weather.feature_settings.model.repository.SaveSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun bindSaveSettingsRepository(saveSettingsRepositoryImpl: SaveSettingsRepositoryImpl): SaveSettingsRepository
}