package com.bignerdranch.android.weather.feature_search_city.presentation.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo

@Composable
fun MyCitiesSection(
    modifier: Modifier = Modifier,
    myCities: List<ShortWeatherInfo>,
    onClick: () -> Unit
) {
    Text(text = "My Cities")
    Spacer(modifier = Modifier.height(8.dp))
    Divider(modifier = Modifier.fillMaxWidth())
    Column(
        modifier = modifier
            .focusable(false)
    ) {
        for(city in myCities) {
            CityWeatherCard(
                modifier = Modifier.padding(vertical = 12.dp),
                weatherInfo = city,
                onClick = onClick
            )
        }
    }
}