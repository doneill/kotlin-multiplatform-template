plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization")
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        if (project.hasProperty("OPENWEATHER_API_KEY")) {
            buildConfigField("String", "OPENWEATHER_API_KEY", project.property("OPENWEATHER_API_KEY") as String)
        } else {
            buildConfigField("String", "OPENWEATHER_API_KEY", "\"\"")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation (project (":lib"))
    implementation (Plugins.KOTLIN)
    implementation (Coroutines.ANDROID)
    implementation (SqlDelight.RUNTIME_JDK)
    implementation (SqlDelight.RUNTIME_DRIVER_ANDROID)
    implementation (SupportLibs.ANDROIDX_APPCOMPAT)
    implementation (SupportLibs.ANDROIDX_CORE_KTX)
    implementation (SupportLibs.ANDROIDX_CONSTRAINT_LAYOUT)
    testImplementation (TestingLib.JUNIT)
    androidTestImplementation (AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
}

// build and run app
task<Exec>("runDebugApp") {
    dependsOn ("installDebug")
    val adb = File(System.getenv("ANDROID_HOME"), "platform-tools${File.separator}adb")
    commandLine (adb, "shell", "am", "start", "-n", "com.jdoneill.ktmultiplatform/com.jdoneill.ktmultiplatform.ui.MainActivity")
}
