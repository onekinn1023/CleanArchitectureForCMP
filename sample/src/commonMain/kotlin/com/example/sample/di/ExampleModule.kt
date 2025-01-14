package com.example.sample.di

import com.example.core.common.DispatcherProvider
import com.example.datastore.DataStoreFactory
import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserTokenDatastore
import com.example.sample.data.MyExampleRepository
import com.example.sample.data.MyExampleRepositoryImpl
import com.example.sample.navigation.di.DecomposeModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.example.sample.presentation.viewmodels")
class ExampleViewModelModule

@Module
@ComponentScan("com.example.sample.domain")
class ExampleDomainModule

@Module
class ExampleDataModule {

    @Single
    fun provideMyExampleRepository(
        dataProvider: DispatcherProvider
    ): MyExampleRepository {
        return MyExampleRepositoryImpl(dataProvider)
    }
}

@Module(
    includes = [ExampleDataModule::class,
        ExampleDomainModule::class, ExampleViewModelModule::class, DecomposeModule::class]
)
class MyExampleModule