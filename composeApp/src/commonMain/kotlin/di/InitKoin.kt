package di

import decompose.di.DecomposeModule
import example.di.ExampleModule
import fileSystem.di.FileSystemModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initKoin(
    config: KoinAppDeclaration? = null,
    logEnabled: Boolean
): KoinApplication {
    return startKoin {
        config?.invoke(this)
        modules(
            platformModule(logEnabled),
            ProviderModule().module,
            ExampleModule().module,
            DecomposeModule().module,
            FileSystemModule().module,
            PlatformDataModule().module,
        )
    }
}