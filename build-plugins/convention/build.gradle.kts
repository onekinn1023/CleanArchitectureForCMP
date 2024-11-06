plugins {
    `kotlin-dsl`
}

group = "com.example.app.buildplugins"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        version = "1.0.0"
        register("kotlinMultiplatform") {
            id = "com.example.app.kotlinMultiplatform"
            implementationClass = "com.example.app.convention.KotlinMultiplatformApplicationPlugin"
        }
        register("kmpConventionLibrary") {
            id = "com.example.app.kmpConventionLibrary"
            implementationClass = "com.example.app.convention.KotlinConventionLibraryPlugin"
        }
        register("kmpKoin") {
            id = "com.example.app.kmpKoinConvention"
            implementationClass = "com.example.app.convention.KMPKoinPlugin"
        }
        register("KotlinModuleConvention") {
            id = "com.example.app.kotlinModuleConvention"
            implementationClass = "com.example.app.convention.KotlinModuleConventionPlugin"
        }
        register("kmpKtor") {
            id = "com.example.app.kmpKtorConvention"
            implementationClass = "com.example.app.convention.KMPKtorPlugin"
        }
        register("compose") {
            id = "com.example.app.compose"
            implementationClass = "com.example.app.convention.ComposeConventionPlugin"
        }
    }
}
