package com.bignerdranch.android.weather.feature_settings.data.repository

import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity
import com.bignerdranch.android.weather.feature_settings.domain.repository.GetMyCitiesRepository
import javax.inject.Inject

class GetMyCitiesRepositoryImpl @Inject constructor(
    private val myCitiesDao: MyCitiesDao
) : GetMyCitiesRepository {

    override suspend fun getMyCities(): List<MyCity> =
        myCitiesDao.getSavedCitiesAsList().map {
            MyCity(
                cityName = it.city,
                id = it.id.toFloat()
            )
        }

}