package com.bignerdranch.android.weather.feature_city_weather.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.alpha
import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.toCityWeather
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.net.URL

class CityWeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : CityWeatherRepository {

    @Suppress("BlockingMethodInNonBlockingContext") // doesn't block the ui thread
    override suspend fun getWeather(city: String): Flow<Result<CityWeather>> = flow {
        try {
            emit(Result.Loading())
            val cityWeatherDto = weatherApi.getCityWeather(city)
            var icon: Bitmap? = null
            val gettingIconBitmap = CoroutineScope(Dispatchers.IO).launch {
                val iconUrl = URL("https://"+cityWeatherDto.current.condition.iconLink)
                icon = BitmapFactory.decodeStream(iconUrl.openConnection().getInputStream())
                // cropping
                assert(icon != null)
                val nonNullIcon = icon!!
                var minX = nonNullIcon.width
                var maxX = 1
                var minY = nonNullIcon.height
                var maxY = 1
                for(y in 1 until nonNullIcon.height) {
                    for(x in 1 until nonNullIcon.width) {
                        val pixelAlpha = android.graphics.Color.alpha(nonNullIcon.getPixel(x, y))
                        if(pixelAlpha == 255) {
                            if(x < minX) minX = x
                            if(y < minY) minY = y
                            if(x > maxX) maxX = x
                            if(y > maxY) maxY = y
                        }
                    }
                }

                log("width $maxX $minX, height $maxY $minY")
                log("width ${maxX-minX}, height ${maxY-minY}")
                icon = Bitmap.createBitmap(nonNullIcon, minX, minY, maxX-minX, maxY-minY)
            }
            gettingIconBitmap.join()
            val cityWeather = cityWeatherDto.toCityWeather()
            cityWeather.icon = icon
            emit(Result.Success(cityWeather))
        } catch (e: HttpException) {
            emit(Result.Error("An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }
}