package di

import decompose.di.componentsModule
import example.di.exampleModule
import fileSystem.di.fileSystemModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null,
    logEnabled: Boolean
): KoinApplication {
    return startKoin {
        config?.invoke(this)
        modules(
            platformModule(logEnabled),
            providerModule,
            exampleModule,
            componentsModule,
            fileSystemModule
        )
    }
}