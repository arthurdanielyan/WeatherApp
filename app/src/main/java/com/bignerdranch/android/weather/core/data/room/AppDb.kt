package com.bignerdranch.android.weather.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo

@Database(entities = [ShortWeatherInfo::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract val myCitiesDao: MyCitiesDao
}