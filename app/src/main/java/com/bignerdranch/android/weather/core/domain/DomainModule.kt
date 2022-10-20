package com.bignerdranch.android.weather.core.domain

import com.bignerdranch.android.weather.core.domain.repository.SharedRepository
import com.bignerdranch.android.weather.core.domain.usecases.GetIconUseCase
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
    fun provideGetIconUseCase(sharedRepository: SharedRepository) =
        GetIconUseCase(sharedRepository, Dispatchers.Default)
}