package com.example.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserTokenDatastore
import okio.Path.Companion.toPath

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DataStoreFactory {
    fun createTokenDataStore(): UserTokenDatastore
    fun createSampleDataStore(): SampleTextsDataStore
}

internal fun commonCreateDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )
}

internal const val DATA_STORE_FILE_NAME = "prefs.preferences_pb"
