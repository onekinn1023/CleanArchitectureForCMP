package com.example.app.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

class KMPKoinPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(target.libs.findPlugin("kotlin-serialization").get().get().pluginId)
            apply(target.libs.findPlugin("ksp").get().get().pluginId)
        }

        target.run {
            extensions.configure<KotlinMultiplatformExtension> {
                with(sourceSets) {
                    androidMain {
                        dependencies {
                            implementation(libs.findLibrary("koin-android").get())
                        }
                    }
                    commonMain.dependencies {
                        api(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                        api(libs.findLibrary("koin-annotations").get())
                    }

                    named("commonMain").configure {
                        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                    }
                }
            }
            configKoinKsp()
        }
    }
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