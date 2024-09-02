package di

import data.MyRepository
import data.MyRepositoryImpl
import data.local.ExampleLocalRepository
import data.local.ExampleLocalRepositoryImpl
import data.remote.ExampleHttpRepository
import data.remote.ExampleHttpRepositoryImpl
import dataStore.remote.ExampleHttpDataSource
import decompose.AppRootComponent
import decompose.DefaultMyScreenComponent
import decompose.DefaultScreenBComponent
import decompose.MyScreenComponent
import decompose.RootComponent
import decompose.ScreenBComponent
import domain.ExampleOperationUseCase
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import presentation.MyViewModel
import presentation.PermissionViewModel
import provider.DefaultDispatcher
import provider.DispatcherProvider

expect val platformModule: Module

val sharedModule = module {
    singleOf(::ExampleHttpDataSource)
    // Data
    single { ExampleHttpDataSource(get(), get()) }
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    single { ExampleHttpRepositoryImpl(get()) }.bind<ExampleHttpRepository>()
    single { ExampleLocalRepositoryImpl(get(), get()) }.bind<ExampleLocalRepository>()
    // Domain
    single { ExampleOperationUseCase(get(), get()) }
    // Presentation
    viewModelOf(::MyViewModel)
    viewModelOf(::PermissionViewModel)
}

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

val providerModule = module {
    single<DispatcherProvider.Factory> {
        DefaultDispatcher.Factory()
    }
}