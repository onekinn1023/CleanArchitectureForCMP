package cmp.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension: TestPluginExtension =
            target.extensions.create("testPlugins", TestPluginExtension::class.java)
        println("This is my custom plugin")
        target.task("pluginTest") {
            doLast {
                println(extension.message)
            }
        }
    }
}

open class TestPluginExtension {
    var message: String = "This is my first plugin"
}