# WeatherApp
A simple weather app using a weather api. In this app is used MVVM pattern with clean architecture and Jetpack Compose. This project is not done yet.

If you want to test this app, please visit the site https://www.weatherapi.com/ and generate a new api key.
Then create a new package in the "core" package named "di" and then create a file called "RetrofitModule.kt" in the "di" package.
Place the following code in the RetrofitModule.kt file and change "your api key" (line 19) to your api key genereated in the mentioned website.


package com.bignerdranch.android.weather.core.di

import com.bignerdranch.android.weather.core.data.WeatherApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
  single {
    val httpClient = OkHttpClient.Builder()

    httpClient.addInterceptor { chain: Interceptor.Chain ->
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("key", "your api key")
            .method(original.method(), original.body())
            .build()

        chain.proceed(request)
    }

    val client = httpClient.build()

    Retrofit.Builder()
        .baseUrl("http://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(WeatherApi::class.java)
  }
}
