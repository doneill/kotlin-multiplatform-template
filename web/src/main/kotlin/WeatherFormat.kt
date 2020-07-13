import react.*
import react.dom.*

external interface WeatherFormatProps: RProps {
    var response: String
}

class WeatherFormat: RComponent<WeatherFormatProps, RState>() {
    override fun RBuilder.render() {
        div {
            +props.response
        }
    }
}

fun RBuilder.weatherFormat(handler: WeatherFormatProps.() -> Unit): ReactElement {
    return child(WeatherFormat::class) {
        this.attrs(handler)
    }
}