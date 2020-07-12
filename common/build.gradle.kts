import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)
    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    android()

    // select iOS target platform depending on the Xcode environment variables
    // iPhone simulator : presets.iosX64 | real iDevice 64 bit : presets.iosArm64
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
    if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
        ::iosArm64
    else
        ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "JDOCommon"
            }
        }
    }

    js {
        browser {  }
    }

    sourceSets["commonMain"].dependencies {
        // kotlin
        implementation(kotlin("stdlib-common", BuildPluginsVersion.KOTLIN))
        // Coroutines
        implementation(Coroutines.COMMON)
        // Ktor
        implementation(Ktor.COMMON_CORE)
        // Serialize
        implementation(Kotlin.SERIALIZATION_COMMON)
        // SQL Delight
        implementation(SqlDelight.RUNTIME)
    }

    sourceSets["androidMain"].dependencies {
        // kotlin
        implementation(kotlin("stdlib", BuildPluginsVersion.KOTLIN))

        // Coroutines
        implementation(Coroutines.JDK)
        implementation(Coroutines.ANDROID)

        // Ktor
        implementation(Ktor.ANDROID)
        // Serialize
        implementation(Kotlin.SERIALIZATION)
        // SQL Delight
        implementation(SqlDelight.RUNTIME_DRIVER_ANDROID)
    }

    sourceSets["iosMain"].dependencies {
        // Coroutines
        implementation(Coroutines.NATIVE)
        // Ktor
        implementation(Ktor.IOS)
        // Serialize
        implementation(Kotlin.SERIALIZATION_IOS)
        // SQL Delight
        implementation(SqlDelight.RUNTIME_DRIVER_IOS)
    }

    sourceSets["jsMain"].dependencies {
        implementation(kotlin("stdlib-js"))
        // Coroutines
        implementation(Coroutines.WEB)
        // Ktor
        implementation(Ktor.WEB)
        // Serialize
        implementation(Kotlin.SERIALIZATION_WEB)
        // SQL Delight
        implementation(SqlDelight.RUNTIME_DRIVER_JS)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    // / selecting the right configuration for the iOS
    // / framework depending on the environment
    // / variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
            .getByName<KotlinNativeTarget>("ios")
            .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    // / generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n" +
                "export 'JAVA_HOME=${System.getProperty("java.home")}'\n" +
                "cd '${rootProject.rootDir}'\n" +
                "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

sqldelight {
    database("KmpDb") {
        packageName = "com.jdoneill.db"
        sourceFolders = listOf("sqldelight")
    }
}

tasks.getByName("build").dependsOn(packForXcode)
