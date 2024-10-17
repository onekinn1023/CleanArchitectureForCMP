package com.example.datastore.di

import org.koin.core.module.Module

expect fun datastoreModule(): Module

//@Module
//@ComponentScan("dataStore.local")
//class NativeModule 跨平台特性创建的class module（context）无法通过注解代理正常生成，因此只能通过手动by inject注入