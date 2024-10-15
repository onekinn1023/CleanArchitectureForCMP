package example.di

import com.example.core.common.DispatcherProvider
import example.data.local.ExampleLocalRepository
import example.data.local.ExampleLocalRepositoryImpl
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

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
}

@Module(includes = [ExampleDataModule::class, ExampleDomainModule::class, ExampleViewModelModule::class])
class ExampleModule