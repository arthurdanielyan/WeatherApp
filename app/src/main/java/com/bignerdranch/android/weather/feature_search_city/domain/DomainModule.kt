package com.bignerdranch.android.weather.feature_search_city.domain

import com.bignerdranch.android.weather.feature_search_city.data.repository.SearchCityRepositoryImpl
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.GetMyCitiesUseCase
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
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
    fun provideSearchCityUseCase(repository: SearchCityRepository): SearchCityUseCase =
        SearchCityUseCase(repository, Dispatchers.Default)

    @Provides
    @ViewModelScoped
    fun provideGetMyCitiesUseCase(repository: SearchCityRepositoryImpl): GetMyCitiesUseCase =
        GetMyCitiesUseCase(repository, Dispatchers.Default)
}