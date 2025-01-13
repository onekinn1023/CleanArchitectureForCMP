package com.example.datastore.di

import com.example.datastore.DataStoreFactory
import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserTokenDatastore
import org.koin.core.module.Module
import org.koin.dsl.module

val dataStoreModule = sharedModule() + module {
    single<SampleTextsDataStore> {
        get<DataStoreFactory>()
            .createSampleDataStore()
    }
    single<UserTokenDatastore> {
        get<DataStoreFactory>()
            .createTokenDataStore()
    }
}

internal expect fun sharedModule(): Module

expect fun datastoreModule(): Module

//@Module
//@ComponentScan("dataStore.local")
//class NativeModule 跨平台特性创建的class module（context）无法通过注解代理正常生成，因此只能通过手动by inject注入