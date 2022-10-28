package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.componentes

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.core.extensions.convertToWeekDay
import com.bignerdranch.android.weather.core.model.Date

@Composable
fun DayInfo(
    modifier: Modifier = Modifier,
    date: Date,
    icon: Bitmap?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0x39979797)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = convertToWeekDay(date.day)
        )
        Text(
            text = "${date.day}/${date.month}"
        )
    }
}