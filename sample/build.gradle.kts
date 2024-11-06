plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cmp.compose.plugin)
    alias(libs.plugins.cmp.kotlin.multiplatform.module)
    alias(libs.plugins.cmp.kotlin.multiplatform.convention.library)
    alias(libs.plugins.cmp.network.plugin)
    alias(libs.plugins.cmp.koin.plugin)
    alias(libs.plugins.ksp)
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
            implementation(project(":ui"))
        }
    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
    arg("KOIN_CONFIG_CHECK","true")
}

android {
    namespace = "com.example.sample"
}
