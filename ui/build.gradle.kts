plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    id("com.example.app.kotlinModuleConvention")
    id("com.example.app.compose")
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
            implementation(project(":network"))
            implementation(project(":filesystem"))
            implementation(project(":datastore"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.ui"
}
