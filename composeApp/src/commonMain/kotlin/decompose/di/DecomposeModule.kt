package decompose.di

import decompose.AppRootComponent
import decompose.DefaultMyScreenComponent
import decompose.DefaultScreenBComponent
import decompose.DefaultUploadFileScreenComponent
import decompose.MyScreenComponent
import decompose.RootComponent
import decompose.ScreenBComponent
import decompose.UploadFileScreenComponent
import org.koin.dsl.module

val componentsModule = module {
    single<ScreenBComponent.Factory> {
        DefaultScreenBComponent.Factory()
    }
    single<MyScreenComponent.Factory> {
        DefaultMyScreenComponent.Factory()
    }

    single<UploadFileScreenComponent.Factory> {
        DefaultUploadFileScreenComponent.Factory()
    }

    single<RootComponent.Factory> {
        AppRootComponent.Factory(
            myScreenComponentFactory = get(),
            screenBComponentFactory = get(),
            uploadFileScreenComponentFactory = get()
        )
    }
}