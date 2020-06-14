package com.jdoneill.ktmultiplatform.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.jdoneill.api.RestApi
import com.jdoneill.ktmultiplatform.BuildConfig
import com.jdoneill.ktmultiplatform.R
import com.jdoneill.common.getDate
import com.jdoneill.model.Main

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

const val APIKEY = BuildConfig.OPENWEATHER_API_KEY

const val DEGREE: String = "\u00B0"

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var api: RestApi

    override val coroutineContext: CoroutineContext
        get() = job + Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()
        api = RestApi()

        getWeather(APIKEY)

        findViewById<TextView>(R.id.date_view).text = getDate()
    }

    private fun getWeather(apiKey: String) {
        api.getWeatherMain(
            location = "47.492820,-121.760330",
            apiKey = apiKey,
            success = ::parseResponse,
            failure = ::handleError
        )
    }

    private fun parseResponse(response: Main) {
        launch(Main) {
            val name = "North Bend Weather"
            val temp = response.temp
            val feelsLikeTemp = response.feels_like
            val tempMax = response.temp_max
            val tempMin = response.temp_min
            val pressure = response.pressure
            val humidity = response.humidity

            val weatherDisplay = getString(R.string.weather_text,
                                                                name,
                                                                temp, DEGREE,
                                                                feelsLikeTemp, DEGREE,
                                                                tempMax, DEGREE,
                                                                tempMin, DEGREE,
                                                                pressure,
                                                                humidity)

            findViewById<TextView>(R.id.weather_view).text = weatherDisplay
        }
    }

    private fun handleError(ex: Throwable) {
        launch(Main) {
            val msg = ex.message ?: "Unknown error"
            Log.d("Error", msg)
        }
    }
}
