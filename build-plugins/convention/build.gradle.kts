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
        register("kotlinMultiplatform") {
            id = "com.example.app.kotlinMultiplatform"
            implementationClass = "com.example.app.convention.KotlinMultiplatformPlugin"
        }
        register("kmpConvention") {
            id = "com.example.app.kmpConventionLibrary"
            implementationClass = "com.example.app.convention.KotlinConventionLibraryPlugin"
        }
        register("kmpKoin") {
            id = "com.example.app.kmpKoinConvention"
            implementationClass = "com.example.app.convention.KMPKoinPlugin"
        }
    }
}
