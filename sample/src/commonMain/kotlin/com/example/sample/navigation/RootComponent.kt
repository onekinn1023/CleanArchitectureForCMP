package com.example.sample.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(index: Int)

    sealed class Child {
        data class ScreenB(val screenBComponent: ScreenBComponent) : Child()
        data class MyScreen(val myScreenComponent: MyScreenComponent) : Child()
        data class UploadFileScreen(
            val uploadFileScreenComponent: UploadFileScreenComponent
        ) : Child()
    }

    fun interface Factory {
        operator fun invoke(componentContext: ComponentContext): RootComponent
    }
}

class AppRootComponent(
    componentContext: ComponentContext,
    private val myScreenComponentFactory: MyScreenComponent.Factory,
    private val screenBComponentFactory: ScreenBComponent.Factory,
    private val uploadFileScreenComponentFactory: UploadFileScreenComponent.Factory
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.MyScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (configuration) {
            Configuration.MyScreen -> {
                RootComponent.Child.MyScreen(
                    myScreenComponentFactory(
                        componentContext = componentContext,
                        navigateClick = { navigation.push(Configuration.ScreenB(it)) }
                    )
                )
            }

            is Configuration.ScreenB -> {
                RootComponent.Child.ScreenB(
                    screenBComponentFactory(
                        componentContext = componentContext,
                        title = configuration.title,
                        backPressed = { navigation.pop() },
                        navigationToUploadFile = { navigation.push(Configuration.UploadFileScreen) }
                    )
                )
            }

            Configuration.UploadFileScreen -> {
                RootComponent.Child.UploadFileScreen(
                    uploadFileScreenComponentFactory(
                        componentContext = componentContext,
                        backPressed = { navigation.pop() }
                    )
                )
            }
        }
    }

    override fun onBackClicked(index: Int) {
        navigation.popTo(index)
    }

    class Factory(
        private val myScreenComponentFactory: MyScreenComponent.Factory,
        private val screenBComponentFactory: ScreenBComponent.Factory,
        private val uploadFileScreenComponentFactory: UploadFileScreenComponent.Factory
    ) : RootComponent.Factory {
        override fun invoke(componentContext: ComponentContext): RootComponent {
            return AppRootComponent(
                componentContext,
                myScreenComponentFactory,
                screenBComponentFactory,
                uploadFileScreenComponentFactory
            )
        }
    }


    @Serializable
    private sealed interface Configuration {

        @Serializable
        data class ScreenB(val title: String) : Configuration

        @Serializable
        data object MyScreen : Configuration

        @Serializable
        data object UploadFileScreen : Configuration
    }
}