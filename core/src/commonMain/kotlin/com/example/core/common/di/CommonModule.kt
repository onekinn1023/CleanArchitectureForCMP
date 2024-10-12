package com.example.core.common.di

import com.example.core.common.DefaultDispatcher
import com.example.core.common.DispatcherProvider
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class CommonModule {
    @Single
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcher()
    }
}

