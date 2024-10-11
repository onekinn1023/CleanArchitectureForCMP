package di

import com.example.core.common.di.CommonModule
import com.example.core.di.CoreModule
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
            CoreModule().module,
            ExampleModule().module,
            DecomposeModule().module,
            FileSystemModule().module,
            PlatformDataModule().module,
        )
    }
}