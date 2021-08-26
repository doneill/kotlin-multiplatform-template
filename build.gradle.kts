buildscript {
    repositories {
        google()
        mavenCentral()
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
    }
}
