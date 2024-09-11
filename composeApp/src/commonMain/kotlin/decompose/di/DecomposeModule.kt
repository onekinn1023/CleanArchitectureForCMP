package decompose.di

import decompose.AppRootComponent
import decompose.DefaultMyScreenComponent
import decompose.DefaultScreenBComponent
import decompose.MyScreenComponent
import decompose.RootComponent
import decompose.ScreenBComponent
import org.koin.dsl.module

val componentsModule = module {
    single<ScreenBComponent.Factory> {
        DefaultScreenBComponent.Factory()
    }
    single<MyScreenComponent.Factory> {
        DefaultMyScreenComponent.Factory()
    }

    single<RootComponent.Factory> {
        AppRootComponent.Factory(
            myScreenComponentFactory = get(),
            screenBComponentFactory = get()
        )
    }
}