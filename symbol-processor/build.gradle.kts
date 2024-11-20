plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {

    jvm()

    sourceSets {

        val jvmMain by getting {
            dependencies {
                implementation(libs.ksp.api)
                implementation(libs.kotlinpoet.ksp)
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }

    }
}
