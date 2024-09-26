package com.example

import org.gradle.api.Plugin
import org.gradle.api.Project

class MobileLibraryPlugins : Plugin<Project> {
    override fun apply(target: Project) {
        val extensions: MobileLibraryPluginsExtensions =
            target.extensions.create("libraryMessage", MobileLibraryPluginsExtensions::class.java)
        println(extensions.message)
    }
}

open class MobileLibraryPluginsExtensions {
    var message: String = "This is my first library plugin"
}