package com.example.core.di

import com.example.core.common.di.CommonModule
import com.example.core.network.di.NetworkModule
import org.koin.core.annotation.Module

@Module(includes = [CommonModule::class, NetworkModule::class])
class CoreModule