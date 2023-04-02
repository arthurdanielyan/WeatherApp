package com.bignerdranch.android.weather.feature_daily_notifications.data

import com.bignerdranch.android.weather.feature_daily_notifications.data.repository.DailyNotificationRepositoryImpl
import com.bignerdranch.android.weather.feature_daily_notifications.domain.repository.DailyNotificationRepository
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
    abstract fun bindNotificationRepository(
        dailyNotificationRepositoryImpl: DailyNotificationRepositoryImpl
    ): DailyNotificationRepository
}