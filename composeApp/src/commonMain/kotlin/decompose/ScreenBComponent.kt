package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.flow
import utils.asValue

interface ScreenBComponent {
    val uiState: Value<String>

    fun backToA()
}

class DefaultScreenBComponent(
    componentContext: ComponentContext,
    val title: String,
    val onBackPressed: () -> Unit
): ScreenBComponent, ComponentContext by componentContext {

    override val uiState: Value<String> = flow {
        emit(title)
    }.asValue(initialValue = "", lifecycle = lifecycle)

    override fun backToA() {
        onBackPressed()
    }
}