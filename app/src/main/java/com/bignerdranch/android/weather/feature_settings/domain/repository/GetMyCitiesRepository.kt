package com.bignerdranch.android.weather.feature_settings.domain.repository

import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity

interface GetMyCitiesRepository {

    suspend fun getMyCities(): List<MyCity>
}