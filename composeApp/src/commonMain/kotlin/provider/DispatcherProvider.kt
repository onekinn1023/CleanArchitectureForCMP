package provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher

    fun interface Factory {
        operator fun invoke(): DispatcherProvider
    }
}

class DefaultDispatcher: DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    class Factory: DispatcherProvider.Factory {
        override fun invoke(): DispatcherProvider {
            return DefaultDispatcher()
        }
    }
}
