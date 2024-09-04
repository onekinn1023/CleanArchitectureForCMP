package di

import data.DbClient
import dataStore.local.DataStoreFactory
import dataStore.remote.createHttpClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean): Module = module {
    singleOf(::DbClient)
    single { createHttpClient() }
    singleOf(::DataStoreFactory)
}.also {
    if (logEnabled) Napier.base(DebugAntilog())
}