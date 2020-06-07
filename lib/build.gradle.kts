import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jlleitschuh.gradle.ktlint") version "9.1.1"
}

kotlin {
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

    jvm("android")

    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.1.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.10.0")
        implementation("io.ktor:ktor-client-core:1.1.2")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.1.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.10.0")
        implementation("io.ktor:ktor-client-android:1.1.2")
    }

    sourceSets["iosMain"].dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.1.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.10.0")
        implementation("io.ktor:ktor-client-ios:1.1.2")
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
