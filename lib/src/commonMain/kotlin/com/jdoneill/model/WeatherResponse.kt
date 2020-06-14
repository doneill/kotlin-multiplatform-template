package com.jdoneill.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val main: Main
)