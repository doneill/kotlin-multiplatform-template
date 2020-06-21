import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
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
        implementation(kotlin("stdlib-common", BuildPluginsVersion.KOTLIN))
        implementation(Coroutines.COMMON)
        implementation(Kotlin.SERIALIZATION_COMMON)
        implementation(Ktor.COMMON_CORE)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", BuildPluginsVersion.KOTLIN))
        implementation(Coroutines.JDK)
        implementation(Coroutines.ANDROID)
        implementation(Kotlin.SERIALIZATION)
        implementation(Ktor.ANDROID)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Coroutines.NATIVE)
        implementation(Kotlin.SERIALIZATION_IOS)
        implementation(Ktor.IOS)
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
