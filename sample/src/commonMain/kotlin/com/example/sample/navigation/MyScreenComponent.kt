package com.example.sample.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext

interface MyScreenComponent {
    fun onAction(action: MyScreenAction)

    sealed class MyScreenAction {
        data class NavigateToNext(val text: String) : MyScreenAction()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            navigateClick: (String) -> Unit
        ): MyScreenComponent
    }
}

class DefaultMyScreenComponent(
    componentContext: ComponentContext,
    private val navigateToNext: (String) -> Unit
) : MyScreenComponent, ComponentContext by componentContext {

    override fun onAction(action: MyScreenComponent.MyScreenAction) {
        when (action) {
            is MyScreenComponent.MyScreenAction.NavigateToNext -> {
                navigateToNext(action.text)
            }
        }
    }

    class Factory: MyScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            navigateClick: (String) -> Unit
        ): MyScreenComponent {
            return DefaultMyScreenComponent(
                componentContext,
                navigateClick
            )
        }
    }
}