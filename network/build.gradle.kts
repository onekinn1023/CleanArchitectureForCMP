import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cmp.kotlin.multiplatform.module)
    alias(libs.plugins.cmp.kotlin.multiplatform.convention.library)
    alias(libs.plugins.cmp.koin.plugin)
    alias(libs.plugins.cmp.network.plugin)
    alias(libs.plugins.ksp)
}

kotlin {

    sourceSets {
        commonMain.configure {
            kotlin.srcDir("build/generated/ksp/commonMain/kotlin")
            dependencies {
                //put your multiplatform dependencies here
                implementation(project(":core"))
            }
        }
    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
    arg("KOIN_CONFIG_CHECK","true")
}

android {
    namespace = "com.example.network"
}

dependencies {
    add("kspCommonMainMetadata", project(":symbol-processor"))
}

tasks.withType<KotlinCompile>().all {
    if (name != "kspCommonMainMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
