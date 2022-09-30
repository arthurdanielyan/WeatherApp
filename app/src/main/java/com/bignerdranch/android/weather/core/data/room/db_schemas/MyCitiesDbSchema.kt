package com.bignerdranch.android.weather.core.data.room.db_schemas

class MyCitiesDbSchema {
    companion object {
        const val TABLE_NAME = "my_cities_table"

        const val COL_CITY = "column_city"
        const val COL_COUNTRY = "column_country"
        const val COL_TEMP_IN_CELSIUS = "column_temp_in_celsius"
        const val COL_TEMP_IN_FAHRENHEIT = "column_temp_in_fahrenheit"
    }
}