package data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dataStore.local.DataStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import utils.LocalError
import utils.Result
import utils.scheduleCatchingLocalWork

interface ExampleLocalRepository {

    val exampleTextFlow: Flow<String>

    suspend fun getLocalData(): Result<String, LocalError>

    suspend fun changeTheLocalData(): Result<Unit, LocalError>
}

class ExampleLocalRepositoryImpl(
    val dataStore: DataStoreFactory
): ExampleLocalRepository {

    private val db : DataStore<Preferences> by lazy {
        dataStore.createExampleDataStore()
    }

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