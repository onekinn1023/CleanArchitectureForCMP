package com.example.app.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureBaseKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val moduleName = path.split(":").drop(1).joinToString(".")
    println("Kotlin-moduleName($moduleName)")
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = moduleName
            isStatic = true
        }
    }
}
