package com.example.sample.navigation

import com.arkivanov.decompose.ComponentContext

interface BoxGameComponent {
    fun back()

    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            back: () -> Unit
        ): BoxGameComponent
    }
}

class DefaultBoxGameComponent(
    componentContext: ComponentContext,
    val popup: () -> Unit
) : BoxGameComponent, ComponentContext by componentContext {

    override fun back() {
        popup()
    }

    class Factory : BoxGameComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            back: () -> Unit
        ): BoxGameComponent {
            return DefaultBoxGameComponent(
                componentContext,
                back
            )
        }
    }
}
