package com.example.app.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinModuleConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(target.libs.findPlugin("androidLibrary").get().get().pluginId)
        }
        target.run {
            extensions.configure<LibraryExtension>(::configBaseAndroidModule)
            extensions.configure<KotlinMultiplatformExtension>(::configureBaseKotlinMultiplatform)
        }
    }
}