package di

import dataStore.local.DataStoreFactory
import dataStore.remote.createHttpClient
import fileSystem.FileHelper
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean): Module = module {
    single { createHttpClient() }
    singleOf(::DataStoreFactory)
    single { FileHelper.SYSTEM }.bind<FileHelper>()
}.also {
    if (logEnabled) Napier.base(DebugAntilog())
}