package com.bignerdranch.android.weather.core.di

import android.content.Context
import androidx.room.Room
import com.bignerdranch.android.weather.core.data.room.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): AppDb =
        Room.databaseBuilder(
            applicationContext,
            AppDb::class.java,
            "weather_app_database"
        ).build()
}