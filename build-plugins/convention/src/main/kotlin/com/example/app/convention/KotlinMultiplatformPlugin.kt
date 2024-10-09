package com.example.app.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(target.pluginManager) {
                apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                val androidLibraryPluginId =
                    target.libs.findPlugin("androidApplication").get().get().pluginId
                if (!plugins.hasPlugin(androidLibraryPluginId)) {
                    apply(androidLibraryPluginId)
                }
                apply(target.libs.findPlugin("kotlin-serialization").get().get().pluginId)
                apply(target.libs.findPlugin("ksp").get().get().pluginId)
            }
            extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
            extensions.configure<BaseAppModuleExtension>(::configureKotlinAndroid)
        }
    }
}
