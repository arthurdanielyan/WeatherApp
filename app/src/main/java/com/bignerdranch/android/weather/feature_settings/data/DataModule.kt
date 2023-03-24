@file:Suppress("unused")

package com.bignerdranch.android.weather.feature_settings.data

import com.bignerdranch.android.weather.feature_settings.data.repository.GetMyCitiesRepositoryImpl
import com.bignerdranch.android.weather.feature_settings.data.repository.SaveSettingsRepositoryImpl
import com.bignerdranch.android.weather.feature_settings.domain.repository.GetMyCitiesRepository
import com.bignerdranch.android.weather.feature_settings.domain.repository.SaveSettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    @Singleton
    fun bindSaveSettingsRepository(
        saveSettingsRepositoryImpl: SaveSettingsRepositoryImpl
    ): SaveSettingsRepository

    @Binds
    @Singleton
    fun bindCityWeatherRepository(
        getMyCitiesRepositoryImpl: GetMyCitiesRepositoryImpl
    ): GetMyCitiesRepository

}