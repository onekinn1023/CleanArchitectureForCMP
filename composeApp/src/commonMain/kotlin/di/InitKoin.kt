package di

import com.example.core.di.CoreModule
import com.example.filesystem.di.FileSystemModule
import com.example.network.di.NetworkModule
import decompose.di.DecomposeModule
import example.di.ExampleModule
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
            NetworkModule().module,
            FileSystemModule().module,
            ExampleModule().module,
            DecomposeModule().module,
        )
    }
}