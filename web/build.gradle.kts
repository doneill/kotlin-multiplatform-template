plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-js"))

    implementation(npm("text-encoding", "0.7.0"))
    implementation(npm("abort-controller", "3.0.0"))
    implementation(npm("bufferutil", "4.0.3"))
    implementation(npm("utf-8-validate", "5.0.5"))
    implementation(npm("fs", "0.0.1-security"))

    //React, React DOM + Wrappers
    implementation(enforcedPlatform(Kotlin.WRAPPERS))
    implementation(Kotlin.REACT)
    implementation(Kotlin.REACT_DOM)
    implementation(npm("react", Versions.KOTLIN_REACT_NPM))
    implementation(npm("react-dom", Versions.KOTLIN_REACT_NPM))

    //Kotlin Styled
    implementation(Kotlin.STYLED)
    implementation(npm("styled-components", "5.3.1"))
    implementation(npm("inline-style-prefixer", "6.0.0"))

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