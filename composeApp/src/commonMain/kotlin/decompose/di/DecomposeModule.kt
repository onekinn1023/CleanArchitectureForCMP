package decompose.di

import decompose.AppRootComponent
import decompose.DefaultMyScreenComponent
import decompose.DefaultScreenBComponent
import decompose.DefaultUploadFileScreenComponent
import decompose.MyScreenComponent
import decompose.RootComponent
import decompose.ScreenBComponent
import decompose.UploadFileScreenComponent
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module

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