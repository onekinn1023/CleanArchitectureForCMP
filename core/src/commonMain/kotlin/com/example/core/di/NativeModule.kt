package com.example.core.di

import org.koin.core.module.Module

// For different platform features we have to manually inject module
expect fun platformModule(logEnabled: Boolean): Module
