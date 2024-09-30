package com.example.app.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroid(
    extension: BaseAppModuleExtension
) = extension.apply {
    val moduleName = path.split(":").drop(2).joinToString(".")
    println("AndroidTargetPlugins-moduleName($moduleName)")
    with(sourceSets.getByName("main")) {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
        resources.srcDirs("src/commonMain/resources")
    }
    val compliedSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
    val minSdks = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
    val targetSdks = libs.findVersion("android-targetSdk").get().requiredVersion.toInt()
    println("Plugins from Android-sdk--$compliedSdk, ${minSdks}, $targetSdks")
    compileSdk = compliedSdk
    defaultConfig {
        minSdk = minSdks
        maxSdk = targetSdks
    }
    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

open class AndroidCorePluginExtension {
    var min = 24
    var compile = 34
}