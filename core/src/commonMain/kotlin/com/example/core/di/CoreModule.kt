package com.example.core.di

import com.example.core.common.di.CommonModule
import org.koin.core.annotation.Module

@Module(includes = [CommonModule::class])
class CoreModule
