package com.jdoneill.api

import com.jdoneill.common.ApplicationDispatcher
import com.jdoneill.model.Main
import com.jdoneill.model.WeatherResponse

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.readText
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class RestApi {

    companion object {
        private const val API_V2 = "/data/2.5/"
        private const val API_V2_WEATHER = "$API_V2/weather"
        private const val BASE_URL = "https://api.openweathermap.org"
    }

    private val client = HttpClient()

    fun getWeatherMain(location: String, apiKey: String, success: (Main) -> Unit, failure: (Throwable) -> Unit) {
        GlobalScope.launch(ApplicationDispatcher) {
            try {
                // location = "lat,lon"
                val lat = location.substringBefore(",", "")
                val lon = location.substringAfter(",", "")

                val response = client.get<HttpStatement> {
                    url("$BASE_URL$API_V2_WEATHER")
                    parameter("units", "imperial")
                    parameter("lat", lat)
                    parameter("lon", lon)
                    parameter("appid", apiKey)
                }.execute()
                Json.nonstrict.parse(WeatherResponse.serializer(), response.readText())
                    .main
                    .also(success)
            } catch (e: Exception) {
                failure(e)
            }
        }
    }
}