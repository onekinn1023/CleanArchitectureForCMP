package di

import dataStore.remote.createHttpClient
import fileSystem.FileHelper
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class PlatformDataModule {

    @Single
    fun provideFileHelper(): FileHelper {
        return FileHelper.SYSTEM
    }

    @Single
    fun provideHttpClient() : HttpClient {
        return createHttpClient()
    }
}

//@Module
//@ComponentScan("dataStore.local")
//class NativeModule

