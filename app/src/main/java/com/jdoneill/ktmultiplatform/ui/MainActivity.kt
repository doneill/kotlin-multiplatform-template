package com.jdoneill.ktmultiplatform.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

import com.jdoneill.api.RestApi
import com.jdoneill.common.KmpDriverFactory
import com.jdoneill.common.createDb
import com.jdoneill.ktmultiplatform.R
import com.jdoneill.common.getDate
import com.jdoneill.db.KmpModelQueries
import com.jdoneill.model.WeatherResponse
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

const val DEGREE: String = "\u00B0"

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var api: RestApi

    private lateinit var kmpQuery: KmpModelQueries

    override val coroutineContext: CoroutineContext
        get() = job + Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val driver = KmpDriverFactory(this)
        val db = createDb(driver)
        kmpQuery = db.kmpModelQueries

        job = Job()
        api = RestApi()

        getWeather()

        refreshButton.setOnClickListener {
            getWeather()
        }

        dateView.text = getDate()
    }

    private fun getWeather() {
        api.getWeather(
            success = ::parseResponse,
            failure = ::handleError
        )
    }

    private fun parseResponse(response: WeatherResponse) {
        launch(Main) {
            val name = response.name
            val temp = response.main.temp
            val id = response.id

            kmpQuery.insertWeather(id, name, temp.toDouble(), getDate())
            val results = kmpQuery.selectAll().executeAsList()

            for (result in results) {
                Log.d("Weather DB Row", result.id.toString() + " | " + result.name + " | " + result.latest_temp)
            }

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

    private fun handleError(ex: Throwable) {
        launch(Main) {
            val msg = ex.message
            Log.d("Weather Response Error", msg)
        }
    }
}
