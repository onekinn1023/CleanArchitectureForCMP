package example.di

import dataStore.local.DataStoreFactory
import example.data.local.ExampleLocalRepository
import example.data.local.ExampleLocalRepositoryImpl
import example.data.remote.ExampleHttpRepository
import example.data.remote.ExampleHttpRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import provider.DispatcherProvider

@Module
@ComponentScan("example.presentation")
class ExampleViewModelModule

@Module
@ComponentScan("example.domain")
class ExampleDomainModule

@Module
@ComponentScan("example.data")
class ExampleDataModule {

    @Single
    fun provideExampleLocalRepository(
//        dataStoreFactory: DataStoreFactory,
        dataProvider: DispatcherProvider
    ): ExampleLocalRepository {
        return ExampleLocalRepositoryImpl(dataProvider)
    }

    @Single
    fun provideExampleHttpRepository(
        httpClient: HttpClient,
        dataProvider: DispatcherProvider
    ): ExampleHttpRepository {
        return ExampleHttpRepositoryImpl(httpClient, dataProvider)
    }
}

@Module(includes = [ExampleDataModule::class, ExampleDomainModule::class, ExampleViewModelModule::class])
class ExampleModule