package com.example.app.convention

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    val extensions: KMPPluginsExtensions =
        extensions.create("KMPMessageConfig", KMPPluginsExtensions::class.java)
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
                    implementation(libs.findLibrary("cmp-napier").get())
                    implementation(libs.findLibrary("kotlinx-datetime").get())
                    if (extensions.isNeedLocalData) {
                        api(libs.findBundle("datastore").get())
                    }
                }
            }
//        koin ksp
//            named("commonMain").configure {
//                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
//            }
        }
    }
}

open class KMPPluginsExtensions {
    var message: String = "This is my first library plugin"
    var isNeedInject: Boolean = true
    var isNeedLocalData: Boolean = true
}

//internal fun Project.configKoinKsp() {
//    dependencies {
//        val compiler = libs.findLibrary("koin-ksp-compiler").get()
//        add("kspCommonMainMetadata", compiler)
//        add("kspAndroid", compiler)
//        add("kspIosX64", compiler)
//        add("kspIosArm64", compiler)
//        add("kspIosSimulatorArm64", compiler)
//    }
//    tasks.withType(KotlinCompilationTask::class.java).configureEach {
//        if (name != "kspCommonMainKotlinMetadata") {
//            dependsOn("kspCommonMainKotlinMetadata")
//        }
//    }
//    ksp {
//        arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
//        arg("KOIN_CONFIG_CHECK","true")
//    }
//}