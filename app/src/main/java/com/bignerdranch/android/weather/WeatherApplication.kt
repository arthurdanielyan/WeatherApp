package com.bignerdranch.android.weather

import android.app.Application
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.constants.planWeatherAlertNotification
import com.bignerdranch.android.weather.core.constants.turnOffNotification
import com.bignerdranch.android.weather.core.domain.usecases.LoadSettingsUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application() {

    // Injected here so that if the launcher screen changes, the injection doesn't need to be changed
    @Inject lateinit var loadSettingsUseCase: LoadSettingsUseCase
    @Inject lateinit var coroutineDispatcher: CoroutineDispatcher

    var settingsLoaded = false

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(coroutineDispatcher + SupervisorJob()).launch {
            loadSettingsUseCase()
            settingsLoaded = true
            if (SettingsStorage.isWeatherAlertNotificationsEnabled) {
                planWeatherAlertNotification(
                    baseContext,
                    LocalTime.of(
                        SettingsStorage.notificationTime.substringBefore(':').toInt(),
                        SettingsStorage.notificationTime.substringAfter(':').toInt()
                    ))
            } else {
                turnOffNotification(baseContext)
            }
        }
    }
}