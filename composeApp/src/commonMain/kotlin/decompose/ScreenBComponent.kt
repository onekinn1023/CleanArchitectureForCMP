package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import io.ktor.util.logging.Logger
import kotlinx.coroutines.flow.flow
import utils.asValue

interface ScreenBComponent {
    val uiState: Value<String>

    fun backToA()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            title: String,
            backPressed: () -> Unit
        ): ScreenBComponent
    }
}

class DefaultScreenBComponent(
    componentContext: ComponentContext,
    private val title: String,
    private val onBackPressed: () -> Unit
) : ScreenBComponent, ComponentContext by componentContext {

    override val uiState: Value<String> = flow {
        emit(title)
    }.asValue(initialValue = "", lifecycle = lifecycle)

    override fun backToA() {
        onBackPressed()
    }

    class Factory : ScreenBComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            title: String,
            backPressed: () -> Unit
        ): ScreenBComponent {
            return DefaultScreenBComponent(
                componentContext,
                title,
                backPressed
            )
        }
    }
}