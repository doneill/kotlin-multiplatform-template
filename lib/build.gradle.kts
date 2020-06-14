import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

kotlin {
    val coroutine_version = "1.3.5-native-mt"
    val ktor_version = "1.3.2"
    val serializer_version = "0.20.0"

    jvm("android")

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
                baseName = "SharedCode"
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutine_version")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializer_version")
        implementation("io.ktor:ktor-client-core:$ktor_version")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializer_version")
        implementation("io.ktor:ktor-client-android:$ktor_version")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutine_version")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializer_version")
        implementation("io.ktor:ktor-client-ios:$ktor_version")
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

tasks.getByName("build").dependsOn(packForXcode)
