package com.bignerdranch.android.weather.core.di

import com.bignerdranch.android.weather.core.data.repository.SharedRepositoryImpl
import com.bignerdranch.android.weather.core.domain.repository.SharedRepository
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
    abstract fun bindSharedRepository(
        sharedRepositoryImpl: SharedRepositoryImpl
    ): SharedRepository
}