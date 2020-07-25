import react.*
import styled.styledDiv

external interface WeatherFormatProps: RProps {
    var response: String
}

class WeatherFormat: RComponent<WeatherFormatProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            +props.response
        }
    }
}

fun RBuilder.weatherFormat(handler: WeatherFormatProps.() -> Unit): ReactElement {
    return child(WeatherFormat::class) {
        this.attrs(handler)
    }
}