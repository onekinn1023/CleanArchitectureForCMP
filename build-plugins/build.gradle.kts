plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
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
    }
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
}