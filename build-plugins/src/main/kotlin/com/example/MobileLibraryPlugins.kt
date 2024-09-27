package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MobileLibraryPlugins : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.plugins) {
            apply("org.jetbrains.kotlin.multiplatform")
        }
        val extensions: MobileLibraryPluginsExtensions =
            target.extensions.create("libraryMessage", MobileLibraryPluginsExtensions::class.java)
        println(extensions.message)
        target.configure<KotlinMultiplatformExtension> {
            with(this.sourceSets) {
                getByName("commonMain").dependencies {
                    implementation(Dependencies.NAPIER)
                    implementation(Dependencies.KOTLIN_DATE)
                    if (extensions.isNeedLocalData) {
                        api(Dependencies.DATASTORE_CORE)
                        api(Dependencies.DATASTORE_PEREFERENCE)
                    }
                }
            }
        }
    }
}

open class MobileLibraryPluginsExtensions {
    var message: String = "This is my first library plugin"
    var isNeedInject: Boolean = true
    var isNeedLocalData : Boolean = true
}