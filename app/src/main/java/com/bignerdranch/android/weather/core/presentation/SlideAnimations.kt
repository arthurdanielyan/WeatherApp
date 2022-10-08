package com.bignerdranch.android.weather.core.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

const val duration = 300


val slideInLeft = slideInHorizontally (
    animationSpec = tween(duration),
    initialOffsetX = {
        it/2
    }
) + fadeIn(tween(duration))

val slideOutLeft = slideOutHorizontally(
    animationSpec = tween(duration),
    targetOffsetX = {
        -it/2
    }
) + fadeOut(tween(duration))

val slideOutRight = slideOutHorizontally(
    animationSpec = tween(duration),
    targetOffsetX = {
        it/2
    }
) + fadeOut(tween(duration))

val slideInRight = slideInHorizontally (
    animationSpec = tween(duration),
    initialOffsetX = {
        -it/2
    }
) + fadeIn(tween(duration))

const val cityCardAppearanceDuration = 300

val cityCardAppearance = slideInHorizontally (
    animationSpec = tween(cityCardAppearanceDuration),
    initialOffsetX = {
        it/2
    }
) + fadeIn(tween(cityCardAppearanceDuration))