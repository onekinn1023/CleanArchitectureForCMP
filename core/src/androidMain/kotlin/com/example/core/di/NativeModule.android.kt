package com.example.core.di

import com.example.core.datastore.DataStoreFactory
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun platformModule(logEnabled: Boolean): Module = module {
    single { DataStoreFactory(get()) }.bind<DataStoreFactory>()
}.also {
    if (logEnabled) {
        Napier.base(DebugAntilog())
    }
}