import com.jdoneill.api.RestApi
import com.jdoneill.common.getDate
import com.jdoneill.model.WeatherResponse

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.js.onClickFunction

import react.*
import react.dom.*
import styled.*

external interface AppState : RState {
    var htmlResponse: String
}

class App : RComponent<RProps, AppState>() {

    private lateinit var api: RestApi

    override fun AppState.init() {
        api = RestApi()
        api.getWeather(
            success = ::parseResponse,
            failure = ::handleError
        )
    }

    private fun parseResponse(response: WeatherResponse) {
        val mainScope = MainScope()
        mainScope.launch {
            val name = response.name
            val temp = response.main.temp

            val feelsLikeTemp = response.main.feels_like
            val tempMax = response.main.temp_max
            val tempMin = response.main.temp_min
            val pressure = response.main.pressure
            val humidity = response.main.humidity

            setState {
                htmlResponse = """
                    $name 
                    Temp: $temp 
                        Feels like: $feelsLikeTemp 
                        Max: $tempMax 
                        Min: $tempMin 
                        Pressure: $pressure 
                        Humidity: $humidity
                """.trimIndent()
            }
        }

    }

    private fun handleError(ex: Throwable) {
        val mainScope = MainScope()
        mainScope.launch {
            val msg = ex.message
            console.log("Weather Response Error", msg)
        }
    }

    override fun RBuilder.render() {
        val date = getDate()

        styledBody {
            css {
                backgroundColor = Color.whiteSmoke
            }
        }

        styledH1 {
            css {
                fontFamily = "Verdana, Geneva, sans-serif"
            }
            +"Multiplatform Playground"
        }
        styledDiv {
            css {
                padding(vertical = 16.px)
                fontFamily = "Tahoma, Geneva, sans-serif"
            }
            weatherFormat {
                response = state.htmlResponse
            }
            p {
                +(date)
            }
        }
        styledButton {
            css {
                padding(all = 8.px)
                borderStyle = BorderStyle.solid
                backgroundColor = Color.yellow
                fontFamily = "Tahoma, Geneva, sans-serif"
            }
            attrs {
                onClickFunction = {
                    api.getWeather(
                        success = ::parseResponse,
                        failure = ::handleError
                    )
                }
            }
            +"Refresh"
        }
    }
}