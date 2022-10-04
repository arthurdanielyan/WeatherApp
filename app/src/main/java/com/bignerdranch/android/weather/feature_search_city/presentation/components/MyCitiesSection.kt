package com.bignerdranch.android.weather.feature_search_city.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    if(myCities.isNotEmpty())
    LazyColumn(
        modifier = modifier
    ) {
        items(myCities.size) {
            CityWeatherCard(
                modifier = Modifier.padding(vertical = 12.dp),
                weatherInfo = myCities[it],
                onClick = onClick
            )
        }
    }
}