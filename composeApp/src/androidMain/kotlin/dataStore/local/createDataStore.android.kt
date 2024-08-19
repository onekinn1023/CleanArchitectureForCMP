package dataStore.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

//fun provideDataStore(context: Context): DataStore<Preferences> {
//    return createDataStore {
//        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
//    }
//}

//actual fun createDataStore(): DataStore<Preferences> {
//    TODO()
//    return commonCreateDataStore {
////        context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
//    }
//}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory(private val context: Context) {
    actual fun createExampleDataStore(): DataStore<Preferences> {
        return commonCreateDataStore {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
    }
}