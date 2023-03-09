package com.bignerdranch.android.weather.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ClickableIcon

@Composable
fun TopBar(
    screenTitle: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .padding(
                top = 18.dp,
                bottom = 36.dp,
                start = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableIcon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Navigate back",
            onClick = navController::popBackStack,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(
            text = screenTitle,
            style = MaterialTheme.typography.h4,
            color = Color.White,
        )
    }
}