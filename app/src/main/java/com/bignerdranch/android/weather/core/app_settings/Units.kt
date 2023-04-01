package com.bignerdranch.android.weather.core.app_settings

sealed interface Units {

    companion object {
        var selectedTempUnitInt = TempUnits.CELSIUS.key
        val selectedTempUnit: Units
            get() {
                if(TempUnits.values()[selectedTempUnitInt].key == selectedTempUnitInt)
                    return TempUnits.values()[selectedTempUnitInt]
                return TempUnits.values().find { // in case if declaration in the enum class is not ordered
                    it.key == selectedTempUnitInt
                }!!
            }
    }

    val key: Int
    val unitOf: String
    val unitName: String
    val options: List<Units>

    enum class TempUnits(override val key: Int, override val unitName: String) : Units {
        CELSIUS(0, "°C"), FAHRENHEIT(1, "°F");

        override val unitOf: String = "Temperature units"
        override val options: List<Units>
            get() = TempUnits.values().toList()
    }
}

