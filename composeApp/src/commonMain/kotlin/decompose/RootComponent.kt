package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(index: Int)

    sealed class Child {
        data class ScreenA(val screenAComponent: ScreenAComponent) : Child()
        data class ScreenB(val screenBComponent: ScreenBComponent) : Child()
    }
}

class AppRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ScreenA,
        handleBackButton = true,
        childFactory = ::createChild
        )

    private fun createChild(
        configuration: Configuration,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (configuration) {
            Configuration.ScreenA -> {
                RootComponent.Child.ScreenA(
                    DefaultScreenAComponent(
                        componentContext = componentContext,
                        navigateToScreenB = {
                            navigation.push(
                                Configuration.ScreenB(
                                    it
                                )
                            )
                        }
                    )
                )
            }

            is Configuration.ScreenB -> {
                RootComponent.Child.ScreenB(
                    DefaultScreenBComponent(
                        componentContext = componentContext,
                        title = configuration.title,
                        onBackPressed = {
                            navigation.pop()
                        }
                    )
                )
            }
        }
    }

    override fun onBackClicked(index: Int) {
        navigation.popTo(index)
    }


    @Serializable
    private sealed interface Configuration {

        @Serializable
        data object ScreenA : Configuration

        @Serializable
        data class ScreenB(val title: String) : Configuration
    }
}