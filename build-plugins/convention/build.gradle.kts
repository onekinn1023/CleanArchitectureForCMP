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
    }
}
