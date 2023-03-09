package com.bignerdranch.android.weather.feature_city_weather.domain.usecases

import com.bignerdranch.android.weather.core.extensions.dayName
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Get3DaysForecastUseCase @Inject constructor (
    private val cityWeatherRepository: CityWeatherRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): Flow<Result<WeatherInfoList>> {
        return withContext(coroutineDispatcher) {
            val resultFlow = cityWeatherRepository.get3DaysForecast(city)
            var newFlow = resultFlow
            newFlow.collect { result ->
                if (result !is Result.Success) return@collect
                val shortForecast = result.data!!
                shortForecast.forecastDays.forEach { forecastDay ->
                    forecastDay.dayName = dayName(forecastDay.date.day)
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
                    flow { emit(Result.Success(WeatherInfoList(forecastDays = sortedList))) }
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