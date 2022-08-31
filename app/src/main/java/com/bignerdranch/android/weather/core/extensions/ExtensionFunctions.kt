package com.bignerdranch.android.weather.core.extensions

fun Double.toIntIfPossible(): String {
    if(this == this.toInt().toDouble()) {
        return this.toInt().toString()
    }
    return this.toString()
}