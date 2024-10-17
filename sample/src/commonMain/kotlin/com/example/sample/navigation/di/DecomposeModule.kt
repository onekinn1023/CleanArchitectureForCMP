package com.example.sample.navigation.di

import com.example.sample.navigation.AppRootComponent
import com.example.sample.navigation.DefaultMyScreenComponent
import com.example.sample.navigation.DefaultScreenBComponent
import com.example.sample.navigation.DefaultUploadFileScreenComponent
import com.example.sample.navigation.MyScreenComponent
import com.example.sample.navigation.RootComponent
import com.example.sample.navigation.ScreenBComponent
import com.example.sample.navigation.UploadFileScreenComponent
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DecomposeModule {

    @Single
    fun provideScreenBComponentFactory(): ScreenBComponent.Factory {
        return DefaultScreenBComponent.Factory()
    }

    @Single
    fun provideMyScreenComponentFactory(): MyScreenComponent.Factory {
        return DefaultMyScreenComponent.Factory()
    }

    @Single
    fun provideUploadFileScreenComponentFactory(): UploadFileScreenComponent.Factory {
        return DefaultUploadFileScreenComponent.Factory()
    }

    @Single
    fun provideRootComponentFactory(
        myScreenComponentFactory: MyScreenComponent.Factory,
        screenBComponentFactory: ScreenBComponent.Factory,
        uploadFileScreenComponentFactory: UploadFileScreenComponent.Factory
    ): RootComponent.Factory {
        return AppRootComponent.Factory(
            myScreenComponentFactory = myScreenComponentFactory,
            screenBComponentFactory = screenBComponentFactory,
            uploadFileScreenComponentFactory = uploadFileScreenComponentFactory
        )
    }
}