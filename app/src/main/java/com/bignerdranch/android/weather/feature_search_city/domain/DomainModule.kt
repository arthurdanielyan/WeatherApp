package com.bignerdranch.android.weather.feature_search_city.domain

import com.bignerdranch.android.weather.feature_search_city.data.repository.SearchCityRepositoryImpl
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

//val domainModule = module {
//    single {
//        SearchCityUseCase(get())
//    }
//}

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideSearchCityUseCase(repository: SearchCityRepository): SearchCityUseCase =
        SearchCityUseCase(repository)
}