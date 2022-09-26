package com.bignerdranch.android.weather.feature_search_city.domain

import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSearchCityUseCase(repository: SearchCityRepository): SearchCityUseCase =
        SearchCityUseCase(repository)
}