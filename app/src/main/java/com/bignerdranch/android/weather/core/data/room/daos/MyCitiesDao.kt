package com.bignerdranch.android.weather.core.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.weather.core.data.room.db_schemas.MyCitiesDbSchema
import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCitiesDao {

    @Query("SELECT * FROM ${MyCitiesDbSchema.TABLE_NAME}")
    fun getSavedCities(): Flow<List<ShortWeatherInfo>>

    @Query("SELECT * FROM ${MyCitiesDbSchema.TABLE_NAME}")
    fun getSavedCitiesAsList(): List<ShortWeatherInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShortWeatherInfo)

    @Query("SELECT * FROM ${MyCitiesDbSchema.TABLE_NAME} WHERE ${MyCitiesDbSchema.COL_ID} = :cityId LIMIT 1")
    suspend fun getCity(cityId: Float): ShortWeatherInfo?
}