package di

import com.example.core.di.CoreModule
import com.example.core.utils.initNapierLog
import com.example.datastore.di.datastoreModule
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
    initNapierLog(logEnabled)
    return startKoin {
        config?.invoke(this)
        modules(
            CoreModule().module,
            NetworkModule().module,
            FileSystemModule().module,
            datastoreModule(),
            ExampleModule().module,
            DecomposeModule().module,
        )
    }
}