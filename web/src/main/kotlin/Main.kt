import react.dom.*
import kotlinx.browser.document

fun main() {
    render(document.getElementById("root")) {
        child(App::class) {}
    }
}