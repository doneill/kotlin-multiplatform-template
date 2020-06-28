package com.jdoneill.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val weather: ArrayList<Weather>,
    val main: Main,
    val sys: Sys,
    val id: Long,
    val name: String
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Sys (
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)