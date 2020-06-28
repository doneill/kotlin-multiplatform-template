package com.jdoneill.ktmultiplatform.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.jdoneill.api.RestApi
import com.jdoneill.ktmultiplatform.BuildConfig
import com.jdoneill.ktmultiplatform.R
import com.jdoneill.common.getDate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

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

        val location = randomWaCoords()
        Log.d("Random", location.toString())
        getWeather(location, APIKEY)

        findViewById<TextView>(R.id.date_view).text = getDate()
    }

    private fun getWeather(latLng: Pair<Double, Double>, apiKey: String) {
        api.getWeather(
            lat = latLng.first.toString(),
            lng = latLng.second.toString(),
            apiKey = apiKey,
            success = ::parseResponse,
            failure = ::handleError
        )
    }

    private fun parseResponse(response: WeatherResponse) {
        launch(Main) {
            val name = response.name
            val temp = response.main.temp
            val feelsLikeTemp = response.main.feels_like
            val tempMax = response.main.temp_max
            val tempMin = response.main.temp_min
            val pressure = response.main.pressure
            val humidity = response.main.humidity

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

    private fun randomWaCoords():Pair<Double, Double> {
        val random = Random.Default

        val lat = (45..49).shuffled().first()
        val latDecimal = random.nextDouble()
        val lng = (-124..-116).shuffled().first()
        val lngDecimal = random.nextDouble()

        val y = lat + latDecimal
        val x = lng + lngDecimal

        return Pair(y, x)
    }

    private fun handleError(ex: Throwable) {
        launch(Main) {
            val msg = ex.message
            Log.d("Weather Response Error", msg)
        }
    }
}
