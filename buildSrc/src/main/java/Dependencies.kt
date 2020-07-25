object Sdk {
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val COMPILE_SDK_VERSION = 29
}

object Versions {
    const val APPCOMPAT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "1.1.3"
    const val COROUTINES = "1.3.5-native-mt"
    const val CORE_KTX = "1.3.0"
    const val KOTLIN_REACT = "16.13.0-pre.93-kotlin-1.3.70"
    const val KOTLIN_REACT_NPM = "16.13.0"
    const val KOTLIN_STYLED = "1.0.0-pre.94-kotlin-1.3.70"
    const val KTOR = "1.3.2"
    const val SERIALIZER = "0.20.0"
    const val SQLDELIGHT = "1.3.0"
    // testing dependencies
    const val ANDROIDX_TEST_EXT = "1.1.1"
    const val ANDROIDX_TEST = "1.2.0"
    const val JUNIT = "4.13"
    const val ESPRESSO_CORE = "3.2.0"
}

object BuildPluginsVersion {
    const val GRADLE = "4.0.0"
    const val KOTLIN = "1.3.72"
    const val KTLINT = "9.2.1"
    const val BUILD_CONFIG = "2.0.2"
}

object Plugins {
    const val GRADLE = "com.android.tools.build:gradle:${BuildPluginsVersion.GRADLE}"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${BuildPluginsVersion.KOTLIN}"
}

object Kotlin {
    const val SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.SERIALIZER}"
    const val SERIALIZATION_COMMON = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.SERIALIZER}"
    const val SERIALIZATION_IOS = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${Versions.SERIALIZER}"
    const val SERIALIZATION_WEB = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.SERIALIZER}"
    const val REACT = "org.jetbrains:kotlin-react:${Versions.KOTLIN_REACT}"
    const val REACT_DOM = "org.jetbrains:kotlin-react-dom:${Versions.KOTLIN_REACT}"
    const val STYLED = "org.jetbrains:kotlin-styled:${Versions.KOTLIN_STYLED}"
}

object Coroutines {
    const val COMMON = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.COROUTINES}"
    const val JDK = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val NATIVE = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.COROUTINES}"
    const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val WEB = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.COROUTINES}"
    const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
}

object Ktor {
    const val COMMON_CORE = "io.ktor:ktor-client-core:${Versions.KTOR}"
    const val ANDROID = "io.ktor:ktor-client-android:${Versions.KTOR}"
    const val IOS = "io.ktor:ktor-client-ios:${Versions.KTOR}"
    const val WEB = "io.ktor:ktor-client-js:${Versions.KTOR}"
    const val WEB_LOGGING = "io.ktor:ktor-client-logging-js:${Versions.KTOR}"
    const val IOS_CORE =     "io.ktor:ktor-client-core-native:${Versions.KTOR}"
    const val COMMON_SERIALIZATION ="io.ktor:ktor-client-serialization:${Versions.KTOR}"
    const val ANDROID_SERIALZATION ="io.ktor:ktor-client-serialization-jvm:${Versions.KTOR}"
    const val IOS_SERIALIZATION ="io.ktor:ktor-client-serialization-native:${Versions.KTOR}"
    const val WEB_SERIALIZATION = "io.ktor:ktor-client-serialization-js:${Versions.KTOR}"
}

object SqlDelight{
    const val GRADLE = "com.squareup.sqldelight:gradle-plugin:${Versions.SQLDELIGHT}"
    const val RUNTIME = "com.squareup.sqldelight:runtime:${Versions.SQLDELIGHT}"
    const val RUNTIME_JDK = "com.squareup.sqldelight:runtime-jvm:${Versions.SQLDELIGHT}"
    const val RUNTIME_DRIVER_COMMON = "com.squareup.sqldelight:sqlite-driver:${Versions.SQLDELIGHT}"
    const val RUNTIME_DRIVER_IOS = "com.squareup.sqldelight:native-driver:${Versions.SQLDELIGHT}"
    const val RUNTIME_DRIVER_ANDROID = "com.squareup.sqldelight:android-driver:${Versions.SQLDELIGHT}"
    const val RUNTIME_DRIVER_JS = "com.squareup.sqldelight:runtime-js:${Versions.SQLDELIGHT}"
}

object SupportLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "com.android.support.constraint:constraint-layout:${Versions.CONSTRAINT_LAYOUT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
}

object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}

object AndroidTestingLib {
    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}