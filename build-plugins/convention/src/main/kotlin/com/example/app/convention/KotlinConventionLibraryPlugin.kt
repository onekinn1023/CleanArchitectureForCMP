package com.example.app.convention

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinConventionLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.pluginManager) {
            apply(target.libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
//            val androidLibrary =
//                target.libs.findPlugin("androidLibrary").get().get().pluginId
//                if (!this.hasPlugin(androidLibrary)) {
//                    apply(androidLibrary)
//                }
            apply(target.libs.findPlugin("kotlin-serialization").get().get().pluginId)
        }
        target.run {
            val extension: KMPLibraryPluginsExtensions =
                extensions.create(
                    "KMPLibraryPluginsExtensions",
                    KMPLibraryPluginsExtensions::class.java
                )
            println(extension.message)
            extensions.configure<BaseAppModuleExtension>(::configBaseAndroidModule)
            extensions.configure<KotlinMultiplatformExtension> {
                afterEvaluate {
                    with(sourceSets) {
                        commonMain {
                            dependencies {
                                // log
                                implementation(libs.findLibrary("cmp-napier").get())
                                implementation(libs.findLibrary("kotlinx-datetime").get())
                                // data
                                if (extension.isNeedLocalData) {
                                    api(libs.findBundle("datastore").get())
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
                    }
                }
            }
        }
    }
}

open class KMPLibraryPluginsExtensions {
    var message: String = "This is KMP library plugin"
    var isNeedLocalData: Boolean = true
}