package com.example.sample.navigation

import com.arkivanov.decompose.ComponentContext

interface UploadFileScreenComponent {
    fun backPressed()
    fun interface Factory {
        operator fun invoke(
            componentContext: ComponentContext,
            backPressed: () -> Unit
        ): UploadFileScreenComponent
    }
}

class DefaultUploadFileScreenComponent(
    componentContext: ComponentContext,
    val onBackPressed: () -> Unit
    ): UploadFileScreenComponent, ComponentContext by componentContext {

    override fun backPressed() {
        onBackPressed()
    }

    class Factory: UploadFileScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            backPressed: () -> Unit
        ): UploadFileScreenComponent {
            return DefaultUploadFileScreenComponent(
                componentContext,
                backPressed
            )
        }

    }

}