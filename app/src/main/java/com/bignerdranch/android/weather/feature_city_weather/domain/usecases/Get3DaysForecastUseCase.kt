package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.core.extensions.convertToWeekDay
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.ShortForecastList
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import java.util.Calendar.*

class Get3DaysForecastUseCase(
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): Flow<Result<ShortForecastList>> {
        return withContext(coroutineDispatcher) {
            val resultFlow = cityWeatherRepository.get3DaysForecast(city)
            var newFlow = resultFlow
            newFlow.collect { result ->
                if (result !is Result.Success) return@collect
                val shortForecast = result.data!!
                shortForecast.forecastDays.forEach { forecastDay ->
                    val day = forecastDay.date.day
                    val today = GregorianCalendar().get(DAY_OF_MONTH)
                    when (day) {
                        today -> {
                            forecastDay.dayName = "Today"
                        }
                        GregorianCalendar().apply { add(DAY_OF_MONTH, 1) }.get(DAY_OF_MONTH) -> {
                            forecastDay.dayName = "Tomorrow"
                        }
                        else -> {
                            forecastDay.dayName = convertToWeekDay(day)
                        }
                    }
                }
                val sortedList = shortForecast.forecastDays.toMutableList().also { forecastDays ->
                    forecastDays.forEachIndexed { index, forecastDay ->
                        if (forecastDay.dayName == "Today" && index != 0) {
                            forecastDays.swap(index, 0)
                        } else if (forecastDay.dayName == "Tomorrow" && index != 1) {
                            forecastDays.swap(index, 1)
                        } else if (forecastDay.dayName !== "Tomorrow" && forecastDay.dayName == "Today" && index != 2) {
                            forecastDays.swap(index, 2)
                        }
                    }
                }
                newFlow =
                    flow { emit(Result.Success(ShortForecastList(forecastDays = sortedList))) }
            }
            return@withContext newFlow
        }
    }



    private fun <T> MutableList<T>.swap(first: Int, second: Int) {
        val secondObj = this[second]
        this[second] = this[first]
        this[first] = secondObj
    }
}