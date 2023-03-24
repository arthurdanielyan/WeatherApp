package com.bignerdranch.android.weather.core.domain.repository

import android.graphics.Bitmap

interface SharedRepository {

    suspend fun getIcon(iconUrl: String): Bitmap
}