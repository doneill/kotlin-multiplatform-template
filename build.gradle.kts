buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath(SqlDelight.GRADLE)
    }
}

plugins {
    id("com.android.application") version BuildPluginsVersion.GRADLE apply false
    kotlin("android") version BuildPluginsVersion.KOTLIN apply false
    kotlin("plugin.serialization") version BuildPluginsVersion.KOTLIN
    id("org.jlleitschuh.gradle.ktlint") version BuildPluginsVersion.KTLINT
    id("com.github.gmazzo.buildconfig") version BuildPluginsVersion.BUILD_CONFIG
}

allprojects {
    group = PUBLISHING_GROUP
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    }
}
