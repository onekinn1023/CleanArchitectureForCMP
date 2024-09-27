import org.jetbrains.kotlin.fir.declarations.builder.buildScript
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.example.library-plugin")
    id("com.example.androidCore-plugin")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.bundles.koin.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.core.splashscreen)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            api(libs.koin.core)
            implementation(libs.bundles.koin.common)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            implementation(libs.bundles.ktor)

            api(libs.bundles.mock.permissions)

            implementation(libs.bundles.decompose)
            implementation(libs.okio)
            implementation(libs.bundles.file.kit)
        }
        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonTest.dependencies {
            implementation(libs.okio.test)
        }
    }
}

android {
    namespace = "org.example.project"

    defaultConfig {
        applicationId = "org.example.project"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

libraryMessage {
    isNeedLocalData = true
    message = "This is first plugin and messge was changed from build"
}