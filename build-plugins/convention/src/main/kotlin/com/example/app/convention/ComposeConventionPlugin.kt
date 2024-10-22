package com.example.app.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(target.libs.findPlugin("jetbrainsCompose").get().get().pluginId)
            apply(target.libs.findPlugin("androidLibrary").get().get().pluginId)
        }
        target.run {
            extensions.configure<KotlinMultiplatformExtension> {
                afterEvaluate {
                    with(sourceSets) {
                        commonMain {
                            dependencies {
                                implementation(libs.findBundle("compose").get())
                            }
                        }
                        androidMain {
                            dependencies {
                                implementation(libs.findLibrary("compose-android-preview").get())
                            }
                        }
                    }
                }
            }
            extensions.configure<LibraryExtension> {
                dependencies {
                    add("debugImplementation", libs.findLibrary("compose-uiTooling").get())
                }
            }
        }
    }
}