package com.example.sample.di

import com.example.core.di.CoreModule
import com.example.core.utils.initNapierLog
import com.example.datastore.di.dataStoreModule
import com.example.filesystem.di.FileSystemModule
import com.example.network.di.NetworkModule
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
        val modules = dataStoreModule + listOf(
            CoreModule().module,
            NetworkModule().module,
            FileSystemModule().module,
            MyExampleModule().module
        )
        modules(modules)
    }
}