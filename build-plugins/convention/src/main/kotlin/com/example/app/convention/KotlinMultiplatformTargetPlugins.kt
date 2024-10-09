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
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    afterEvaluate {
        with(sourceSets) {
            commonMain {
                dependencies {
                    // log
                    implementation(libs.findLibrary("cmp-napier").get())
                    implementation(libs.findLibrary("kotlinx-datetime").get())
                    // data
                    if (extensions.isNeedLocalData) {
                        api(libs.findBundle("datastore").get())
                    }
                    // inject
                    if (extensions.isNeedInject) {
                        api(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                        api(libs.findLibrary("koin-annotations").get())
                    }
                    // file
                    implementation(libs.findLibrary("okio").get())
                    implementation(libs.findBundle("file-kit").get())
                    // permission
                    api(libs.findBundle("mock-permissions").get())
                    // navigation
                    implementation(libs.findBundle("decompose").get())
                    // viewModel
                    implementation(libs.findLibrary("lifecycle-viewmodel").get())
                }
            }
            androidMain {
                dependencies {
                    implementation(libs.findLibrary("koin-android").get())
                }
            }
            commonTest {
                dependencies {
                    implementation(libs.findLibrary("okio-test").get())
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
    var message: String = "This is my first library plugin"
    var isNeedInject: Boolean = true
    var isNeedLocalData: Boolean = true
}

private fun Project.configKoinKsp() {
    dependencies {
        val compiler = libs.findLibrary("koin-ksp-compiler").get()
        add("kspCommonMainMetadata", compiler)
        add("kspAndroid", compiler)
        add("kspIosX64", compiler)
        add("kspIosArm64", compiler)
        add("kspIosSimulatorArm64", compiler)
    }
    tasks.withType(KotlinCompilationTask::class.java).configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}
