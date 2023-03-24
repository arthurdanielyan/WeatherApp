package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionItem(
    optionName: String,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = if(selected) Color(0x432D2DFF) else Color.Transparent)
            .padding(16.dp)
    ) {
        Text(
            text = optionName,
            color = if(selected) Color(0xFF6969FF) else Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}