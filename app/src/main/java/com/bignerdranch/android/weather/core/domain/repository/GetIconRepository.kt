package com.bignerdranch.android.weather.core.domain.repository

import android.graphics.Bitmap

interface GetIconRepository {

    suspend fun getIcon(iconUrl: String): Bitmap
}