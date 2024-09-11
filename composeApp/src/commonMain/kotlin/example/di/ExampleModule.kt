package example.di

import example.data.MyRepository
import example.data.local.ExampleLocalRepository
import example.data.local.ExampleLocalRepositoryImpl
import example.data.remote.ExampleHttpRepository
import example.data.remote.ExampleHttpRepositoryImpl
import example.domain.ExampleOperationUseCase
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import example.data.MyRepositoryImpl
import example.presentation.MyViewModel
import example.presentation.PermissionViewModel

val exampleModule = module {
    // Data
    singleOf(::MyRepositoryImpl).bind<MyRepository>()
    single { ExampleHttpRepositoryImpl(get(), get()) }.bind<ExampleHttpRepository>()
    single { ExampleLocalRepositoryImpl(get(), get()) }.bind<ExampleLocalRepository>()
    // Domain
    single { ExampleOperationUseCase(get(), get()) }
    // Presentation
    viewModelOf(::MyViewModel)
    viewModelOf(::PermissionViewModel)
}