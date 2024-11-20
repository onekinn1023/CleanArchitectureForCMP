plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.cmp.kotlin.multiplatform.module)
    alias(libs.plugins.cmp.kotlin.multiplatform.convention.library)
    alias(libs.plugins.ksp)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
        }
    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
    arg("KOIN_CONFIG_CHECK","true")
}

android {
    namespace = "com.example.datastore"
}
