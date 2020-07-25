plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(npm("text-encoding"))
    implementation(npm("abort-controller"))
    implementation(npm("bufferutil"))
    implementation(npm("utf-8-validate"))
    implementation(npm("fs"))

    //React, React DOM + Wrappers
    implementation(Kotlin.REACT)
    implementation(Kotlin.REACT_DOM)
    implementation(npm("react", Versions.KOTLIN_REACT_NPM))
    implementation(npm("react-dom", Versions.KOTLIN_REACT_NPM))

    //Kotlin Styled
    implementation(Kotlin.STYLED)
    implementation(npm("styled-components"))
    implementation(npm("inline-style-prefixer"))

    implementation(project(":common"))
}

kotlin {
    target {
        useCommonJs()
        browser {
            // https://kotlinlang.org/docs/reference/javascript-dce.html#known-issue-dce-and-ktor
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }
}