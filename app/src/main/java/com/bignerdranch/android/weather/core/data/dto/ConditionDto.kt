package com.bignerdranch.android.weather.core.data.dto

import com.google.gson.annotations.SerializedName

data class ConditionDto(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconUrl: String
)