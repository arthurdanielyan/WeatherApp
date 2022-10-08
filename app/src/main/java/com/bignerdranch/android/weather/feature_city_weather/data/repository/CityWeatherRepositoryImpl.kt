package com.bignerdranch.android.weather.feature_city_weather.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import com.bignerdranch.android.weather.core.API_ERROR_MESSAGE
import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.toCityWeather
import com.bignerdranch.android.weather.core.data.dto.toShortForecast
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.model.ShortForecast
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.URL
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor (
    private val weatherApi: WeatherApi,
    private val myCitiesDao: MyCitiesDao
) : CityWeatherRepository {

    private lateinit var iconLink: String

//    private val myCitiesDao = appDatabase.myCitiesDao

    override suspend fun getWeather(city: String): Flow<Result<CityWeather>> = flow {
        try {
            emit(Result.Loading())
            val cityWeatherDto = weatherApi.getCityWeather(city)
            iconLink = cityWeatherDto.current.condition.iconLink
            emit(Result.Success(cityWeatherDto.toCityWeather()))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext") // doesn't block the ui thread
    override suspend fun getIcon(): Bitmap {
        var icon: Bitmap?
        val gettingIconBitmap = CoroutineScope(Dispatchers.IO).async {
            val iconUrl = URL("https://$iconLink")
            icon = BitmapFactory.decodeStream(iconUrl.openConnection().getInputStream())
            // cropping
            assert(icon != null)
            val nonNullIcon = icon!!
            var minX = nonNullIcon.width
            var maxX = 1
            var minY = nonNullIcon.height
            var maxY = 1
            for (y in 1 until nonNullIcon.height) {
                for (x in 1 until nonNullIcon.width) {
                    val pixelAlpha = Color.alpha(nonNullIcon.getPixel(x, y))
                    if (pixelAlpha == 255) {
                        if (x < minX) minX = x
                        if (y < minY) minY = y
                        if (x > maxX) maxX = x
                        if (y > maxY) maxY = y
                    }
                }
            }
            Bitmap.createBitmap(nonNullIcon, minX, minY, maxX - minX, maxY - minY)
        }
        return gettingIconBitmap.await()
    }

    override suspend fun get3DaysForecast(city: String): Flow<Result<ShortForecast>> = flow {
        try {
            emit(Result.Loading())
            val shortForecast = weatherApi.getForecast(city)
            emit(Result.Success(shortForecast.toShortForecast()))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun saveCity(city: ShortWeatherInfo) {
        myCitiesDao.insert(city)
    }
}