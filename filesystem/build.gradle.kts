plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cmp.kotlin.multiplatform.module)
    alias(libs.plugins.cmp.kotlin.multiplatform.convention.library)
    alias(libs.plugins.cmp.koin.plugin)
    alias(libs.plugins.ksp)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(project(":core"))
            implementation(project(":network"))
        }
    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
    arg("KOIN_CONFIG_CHECK","true")
}

android {
    namespace = "com.example.filesystem"
}