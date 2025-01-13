package com.example.datastore.core

import kotlinx.coroutines.flow.Flow

interface DataStoreIndicator<out T, in I> {

    val data: Flow<T>

    suspend fun upsert(i: I)

    suspend fun remove(i: I)

    suspend fun clear()
}