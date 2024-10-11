package decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.example.core.navigatioin.asValue
import kotlinx.coroutines.flow.flow

interface ScreenBComponent {
    val uiState: Value<String>

    fun backToA()

    fun navigationToUploadFileScreen()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            title: String,
            backPressed: () -> Unit,
            navigationToUploadFile: () -> Unit
        ): ScreenBComponent
    }
}

class DefaultScreenBComponent(
    componentContext: ComponentContext,
    private val title: String,
    private val onBackPressed: () -> Unit,
    private val navigationToUploadFile: () -> Unit
) : ScreenBComponent, ComponentContext by componentContext {

    override val uiState: Value<String> = flow {
        emit(title)
    }.asValue(initialValue = "", lifecycle = lifecycle)

    override fun backToA() {
        onBackPressed()
    }

    override fun navigationToUploadFileScreen() {
        navigationToUploadFile()
    }

    class Factory : ScreenBComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            title: String,
            backPressed: () -> Unit,
            navigationToUploadFile: () -> Unit
        ): ScreenBComponent {
            return DefaultScreenBComponent(
                componentContext,
                title,
                backPressed,
                navigationToUploadFile
            )
        }
    }
}