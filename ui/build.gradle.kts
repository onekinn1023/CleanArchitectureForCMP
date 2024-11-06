plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cmp.kotlin.multiplatform.module)
    alias(libs.plugins.cmp.compose.plugin)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {
            implementation(libs.navigation.compose)
            implementation(project(":core"))
        }
    }
}

android {
    namespace = "com.example.ui"
}
