package com.example.app.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(target.pluginManager) {
                apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(target.libs.findPlugin("androidApplication").get().get().pluginId)
            }
            extensions.configure<KotlinMultiplatformExtension>(::configureBaseKotlinMultiplatform)
            extensions.configure<BaseAppModuleExtension> {
                configBaseAndroidModule(this)
                configureKotlinAndroidTarget(this)
            }
        }
    }
}
