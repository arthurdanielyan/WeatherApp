package com.bignerdranch.android.weather.core.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import com.bignerdranch.android.weather.core.domain.repository.SharedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.net.URL
import javax.inject.Inject

class SharedRepositoryImpl @Inject constructor() : SharedRepository {

    @Suppress("BlockingMethodInNonBlockingContext") // doesn't block the ui thread
    override suspend fun getIcon(iconUrl: String): Bitmap {
        var icon: Bitmap?
        val gettingIconBitmap = CoroutineScope(Dispatchers.IO).async {
            val iconFullUrl = URL("https://$iconUrl")
            icon = BitmapFactory.decodeStream(iconFullUrl.openConnection().getInputStream())
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
}