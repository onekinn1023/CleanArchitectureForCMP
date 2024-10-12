package com.example.core.di

import org.koin.core.module.Module

// For different platform features we have to manually inject module
expect fun platformModule(logEnabled: Boolean): Module

//@Module
//@ComponentScan("dataStore.local")
//class NativeModule 跨平台特性创建的class module（context）无法通过注解代理正常生成，因此只能通过手动by inject注入