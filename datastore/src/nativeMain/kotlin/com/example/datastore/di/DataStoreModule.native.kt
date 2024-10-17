package com.example.datastore.di

import com.example.datastore.DataStoreFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


actual fun datastoreModule(): Module = module {
    singleOf(::DataStoreFactory)
}