import com.jdoneill.api.RestApi
import com.jdoneill.common.getDate
import com.jdoneill.model.WeatherResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.*
import react.dom.*

external interface AppState : RState {
    var htmlResponse: String
}

class App : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        val api = RestApi()
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

        h1 {
            +"Multiplatform Playground"
        }
        div {
            weatherFormat {
                response = state.htmlResponse
            }
            p {
                +(date)
            }
        }
    }
}