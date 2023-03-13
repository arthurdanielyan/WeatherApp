package com.bignerdranch.android.weather.feature_search_city.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.data.room.db_schemas.MyCitiesDbSchema
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible

@Entity(tableName = MyCitiesDbSchema.TABLE_NAME)
data class ShortWeatherInfo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MyCitiesDbSchema.COL_CITY)                val city: String,
    @ColumnInfo(name = MyCitiesDbSchema.COL_COUNTRY)             val country: String,
    @ColumnInfo(name = MyCitiesDbSchema.COL_TEMP_IN_CELSIUS)     val tempInCelsius: Double,
    @ColumnInfo(name = MyCitiesDbSchema.COL_TEMP_IN_FAHRENHEIT)  val tempInFahrenheit: Double
) {
    fun getTemp(): String =
        when(Units.selectedTempUnit) {
            Units.TempUnits.CELSIUS -> {
                tempInCelsius.toIntIfPossible()
            }
            Units.TempUnits.FAHRENHEIT -> {
                tempInFahrenheit.toIntIfPossible()
            }
        } + " ${Units.selectedTempUnit.unitName}"

}

