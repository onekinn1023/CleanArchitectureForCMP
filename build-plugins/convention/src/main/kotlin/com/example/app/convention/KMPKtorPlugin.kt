package com.example.app.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPKtorPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(target.libs.findPlugin("kotlin-serialization").get().get().pluginId)
        }

        target.run {
            extensions.configure<KotlinMultiplatformExtension> {
                afterEvaluate {
                    with(sourceSets) {
                        commonMain {
                            dependencies {
                                // network
                                implementation(libs.findBundle("ktor").get())
                            }
                        }
                        androidMain {
                            dependencies {
                                implementation(libs.findLibrary("ktor-client-okhttp").get())
                            }
                        }
                        nativeMain {
                            dependencies {
                                implementation(libs.findLibrary("ktor-client-darwin").get())
                            }
                        }
                    }
                }
            }
        }
    }
}