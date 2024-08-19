package di

import data.DbClient
import dataStore.local.DataStoreFactory
import dataStore.remote.createHttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    singleOf(::DbClient)
    single { createHttpClient() }
    singleOf(::DataStoreFactory)
}