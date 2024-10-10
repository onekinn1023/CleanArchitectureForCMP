package com.example.app.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroidTarget(
    extension: BaseAppModuleExtension
) = extension.apply {
    with(sourceSets.getByName("main")) {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

internal fun Project.configBaseAndroidModule(
    extension: BaseAppModuleExtension
) = extension.apply {
    val compliedSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
    val minSdks = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
    val targetSdks = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
    println("Plugins from Android-sdk--$compliedSdk, ${minSdks}, $targetSdks")
    compileSdk = compliedSdk
    defaultConfig {
        minSdk = minSdks
        maxSdk = targetSdks
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
