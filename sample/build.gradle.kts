plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    id("com.example.app.kotlinModuleConvention")
    id("com.example.app.kmpConventionLibrary")
    id("com.example.app.kmpKtorConvention")
    id("com.example.app.kmpKoinConvention")
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

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

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
    arg("KOIN_CONFIG_CHECK","true")
}

android {
    namespace = "com.example.sample"
}
