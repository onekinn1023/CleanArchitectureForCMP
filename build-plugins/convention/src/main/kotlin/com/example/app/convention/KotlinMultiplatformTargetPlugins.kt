package com.example.app.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provider.inLenientMode
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val extensions: KMPPluginsExtensions =
        extensions.create("KMPMessageConfig", KMPPluginsExtensions::class.java)
    println(extensions.message)
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
    afterEvaluate {
        with(sourceSets) {
            commonMain {
                dependencies {
                    // inject
                    if (extensions.isNeedInject) {
                        api(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                        api(libs.findLibrary("koin-annotations").get())
                    }
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.findLibrary("koin-android").get())
                }
            }
            // koin dynamically generated code
            named("commonMain").configure {
                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
        }
    }
    configKoinKsp()
}

open class KMPPluginsExtensions {
    var message: String = "This is my first KMP plugin"
    var isNeedInject: Boolean = true
}
