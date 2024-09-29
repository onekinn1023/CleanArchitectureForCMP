package example.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import provider.DispatcherProvider
import provider.SchedulePort
import dataStore.local.DataStoreFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.mp.KoinPlatform
import utils.LocalError
import utils.Result

interface ExampleLocalRepository {

    val exampleTextFlow: Flow<String>

    suspend fun getLocalData(): Result<String, LocalError>

    suspend fun changeTheLocalData(): Result<Unit, LocalError>
}

class ExampleLocalRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider
) : ExampleLocalRepository, SchedulePort(), KoinComponent {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.io

    private val dataStoreFactory: DataStoreFactory by inject()

    private val db: DataStore<Preferences> by lazy { dataStoreFactory.createExampleDataStore() }

    private val exampleKey = stringPreferencesKey(EXAMPLE_KEY)

    override val exampleTextFlow: Flow<String> = db.data.map { it[exampleKey] ?: "This test" }

    override suspend fun getLocalData(): Result<String, LocalError> {
        return scheduleCatchingLocalWork {
            exampleTextFlow.first()
        }
    }

    override suspend fun changeTheLocalData(): Result<Unit, LocalError> {
        return scheduleCatchingLocalWork {
            db.edit {
                it[exampleKey] = "Changed text"
            }
        }
    }

    companion object {
        const val EXAMPLE_KEY = "APP_TEST"
    }
}