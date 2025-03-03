package com.example.sample.navigation

import com.arkivanov.decompose.ComponentContext

interface MyScreenComponent {
    fun onAction(action: MyScreenAction)

    sealed class MyScreenAction {
        data class NavigateToNext(val text: String) : MyScreenAction()
        data object NavigateBoxGame : MyScreenAction()
    }

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            navigateClick: (String) -> Unit,
            navigateToGame: () -> Unit
        ): MyScreenComponent
    }
}

class DefaultMyScreenComponent(
    componentContext: ComponentContext,
    private val navigateToNext: (String) -> Unit,
    private val jumpToBoxGame: () -> Unit
) : MyScreenComponent, ComponentContext by componentContext {

    override fun onAction(action: MyScreenComponent.MyScreenAction) {
        when (action) {
            is MyScreenComponent.MyScreenAction.NavigateToNext -> {
                navigateToNext(action.text)
            }

            MyScreenComponent.MyScreenAction.NavigateBoxGame -> {
                jumpToBoxGame()
            }
        }
    }

    class Factory : MyScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            navigateClick: (String) -> Unit,
            navigateToGame: () -> Unit
        ): MyScreenComponent {
            return DefaultMyScreenComponent(
                componentContext,
                navigateClick,
                navigateToGame
            )
        }
    }
}