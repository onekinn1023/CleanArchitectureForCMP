plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    add("compileOnly", kotlin("gradle-plugin"))
    add("compileOnly", kotlin("gradle-plugin-api"))
}

gradlePlugin {
    plugins {
        create("library") {
            id = "com.example.library-plugin"
            implementationClass = "com.example.MobileLibraryPlugins"
        }
        create("android-core") {
            id = "com.example.androidCore-plugin"
            implementationClass = "com.example.AndroidCorePlugins"
        }
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
}