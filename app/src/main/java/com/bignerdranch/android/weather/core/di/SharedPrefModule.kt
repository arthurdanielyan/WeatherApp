package com.bignerdranch.android.weather.core.di

import android.content.Context
import android.content.SharedPreferences
import com.bignerdranch.android.weather.WeatherApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences =
        (context as WeatherApplication).getSharedPreferences(
            "weather_app_settings",
            Context.MODE_PRIVATE
        )
}