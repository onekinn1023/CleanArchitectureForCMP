plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    id("com.example.app.kotlinModuleConvention")
    id("com.example.app.kmpConventionLibrary")
    id("com.example.app.kmpKtorConvention")
    id("com.example.app.kmpKoinConvention")
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(project(":network"))
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
    namespace = "com.example.core"
}
