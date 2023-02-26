package com.bignerdranch.android.weather.feature_search_city.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.core.presentation.cityCardAppearance
import com.bignerdranch.android.weather.core.presentation.slideOutRight
import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo

@Composable
fun MyCitiesSection(
    modifier: Modifier = Modifier,
    myCities: List<ShortWeatherInfo>,
    onClick: (city: String) -> Unit
) {
    Text(text = "My Cities")
    Spacer(modifier = Modifier.height(8.dp))
    Divider(modifier = Modifier.fillMaxWidth())

    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    var itemCount by remember { mutableStateOf(0) }
    var animPlayed by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        itemCount = myCities.size
    }

    val itemCountAnim by animateIntAsState(
        targetValue = itemCount,
        animationSpec = tween(1000),
        finishedListener = {
            animPlayed = true
        }
    )

    if (myCities.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
        ) {
            items(if (!animPlayed) itemCountAnim else myCities.size) {
                AnimatedVisibility(
                    visibleState = visibleState,
                    enter = cityCardAppearance,
                    exit = slideOutRight
                ) {
                    CityWeatherCard(
                        modifier = Modifier.padding(vertical = 12.dp),
                        weatherInfo = myCities[it],
                        onClick = onClick
                    )
                }
            }
        }
    }
}