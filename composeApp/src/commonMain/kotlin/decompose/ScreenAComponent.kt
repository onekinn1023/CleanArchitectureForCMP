package decompose

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.coroutineScope

interface ScreenAComponent {

    fun onEvent(event: Event)

    sealed class Event {
        data class NavigateToScreenB(val title: String): Event()
    }
}

class DefaultScreenAComponent(
    componentContext: ComponentContext,
    val navigateToScreenB: (String) -> Unit
): ScreenAComponent, ComponentContext by componentContext {

    override fun onEvent(event: ScreenAComponent.Event) {
        when(event) {
            is ScreenAComponent.Event.NavigateToScreenB -> navigateToScreenB(event.title)
        }
    }
}