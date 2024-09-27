package com.example

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidCorePlugins : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply("com.android.application")
        }
        val extension: AndroidCorePluginExtension = target.extensions.create(
            "androidBuildConfig", AndroidCorePluginExtension::class.java
        )
        target.configure<BaseAppModuleExtension> {
            with(sourceSets.getByName("main")) {
                manifest.srcFile("src/androidMain/AndroidManifest.xml")
                res.srcDirs("src/androidMain/res")
                resources.srcDirs("src/commonMain/resources")
            }
            compileSdk = extension.compile
            defaultConfig {
                minSdk = extension.min
                maxSdk = extension.target
            }
            buildTypes {
                getByName("debug") {
                    isDebuggable = true
                    isMinifyEnabled = false
                    applicationIdSuffix = ".debug"
                    versionNameSuffix = "-DEBUG"
                }
            }
            buildFeatures {
                compose = true
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }
    }
}

open class AndroidCorePluginExtension {
    var min = 24
    var compile = 34
    var target = compile
}