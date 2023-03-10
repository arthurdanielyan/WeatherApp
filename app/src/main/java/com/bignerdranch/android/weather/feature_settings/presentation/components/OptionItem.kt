package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.app_settings.Units

@Composable
fun OptionItem(
    item: Units,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if(selected) Color(0x432D2DFF) else Color.Transparent)
            .padding(16.dp)
    ) {
        Text(
            text = item.unitName,
            color = if(selected) Color(0xFF6969FF) else Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}