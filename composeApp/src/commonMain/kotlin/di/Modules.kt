package di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import provider.DefaultDispatcher
import provider.DispatcherProvider

expect fun platformModule(logEnabled: Boolean): Module

val providerModule = module {
    single<DispatcherProvider> {
        DefaultDispatcher()
    }
}

