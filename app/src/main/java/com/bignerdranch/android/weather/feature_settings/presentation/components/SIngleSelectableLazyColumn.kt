package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun <T> SingleSelectableLazyColumn (
    modifier: Modifier = Modifier,
    unselectedItem: @Composable (item: T) -> Unit,
    selectedItem: @Composable (item: T) -> Unit,
    onSelect: (item: T) -> Unit,
    selectedItemIndex: Int,
    items: List<T>
) {
    var selectedItemIndexUpt by remember { mutableStateOf(selectedItemIndex) }
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items) {index, item ->
            Box(
                modifier = Modifier.clickable {
                    onSelect(item)
                    selectedItemIndexUpt = index
                }
            ) {
                if (index == selectedItemIndexUpt) selectedItem(item)
                else unselectedItem(item)
            }
        }
    }
}