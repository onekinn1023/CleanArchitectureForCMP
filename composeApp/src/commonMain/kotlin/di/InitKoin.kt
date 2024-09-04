package di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null,
    logEnabled: Boolean
): KoinApplication {
    return startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule(logEnabled), componentsModule, providerModule)
    }
}