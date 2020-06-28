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
}

allprojects {
    group = PUBLISHING_GROUP
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
