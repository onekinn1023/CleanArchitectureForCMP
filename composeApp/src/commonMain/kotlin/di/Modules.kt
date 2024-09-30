package di

import org.koin.core.annotation.ComponentScan
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import provider.DefaultDispatcher
import provider.DispatcherProvider

expect fun platformModule(logEnabled: Boolean): Module

@org.koin.core.annotation.Module
@ComponentScan("provider")
class ProviderModule
