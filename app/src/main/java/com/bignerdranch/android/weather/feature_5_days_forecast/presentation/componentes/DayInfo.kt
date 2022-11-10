package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.componentes

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.core.extensions.dayName
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Date

@Composable
fun DayInfo(
    modifier: Modifier = Modifier,
    date: Date,
    icon: Bitmap?
) {
    log("DayInfo composition")
    var largerText by remember { mutableStateOf(0f) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .background(
                color = Color(0x39979797)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayName(date.day),
            modifier = Modifier
                .onGloballyPositioned {
                    if(largerText < it.boundsInWindow().size.width)
                        largerText = it.boundsInWindow().size.width
                }
        )
        Text(
            text = "${date.day}/${date.month}",
            modifier = Modifier
                .onGloballyPositioned {
                    if(largerText < it.boundsInWindow().size.width)
                        largerText = it.boundsInWindow().size.width
                }
        )
        if(icon != null)
        Image(
            bitmap = icon.asImageBitmap(),
            modifier = Modifier
                .width((largerText / LocalDensity.current.density).dp),
            contentDescription = "Daily Weather Icon",
            contentScale = ContentScale.Inside
        )
    }
}