package com.example.sample.data

import com.example.core.common.DispatcherProvider
import com.example.core.common.LocalError
import com.example.core.common.Result
import com.example.core.common.SchedulePort
import com.example.datastore.data.SampleText
import com.example.datastore.data.SampleTextsDataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface MyExampleRepository {

    fun helloWorld(): String

    val sampleTextFlow: Flow<String>

    suspend fun getLocalData(): Result<String, LocalError>

    suspend fun changeTheLocalData(): Result<Unit, LocalError>
}

class MyExampleRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider
) : MyExampleRepository, SchedulePort(), KoinComponent {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.io

    private val sampleDataStore: SampleTextsDataStore by inject()

    override fun helloWorld(): String {
        return "Hello World!"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val sampleTextFlow: Flow<String>
        get() = sampleDataStore.data.mapLatest { lists ->
            lists.find { it.sampleKey == EXAMPLE_KEY }?.text ?: "This is the test"
        }.distinctUntilChanged()

    override suspend fun getLocalData(): Result<String, LocalError> {
        return scheduleCatchingLocalWork {
            sampleTextFlow.first()
        }
    }

    override suspend fun changeTheLocalData(): Result<Unit, LocalError> {
        return scheduleCatchingLocalWork {
            val changedTextData = SampleText(
                sampleKey = EXAMPLE_KEY,
                text = "Changed text through 2.0"
            )
            sampleDataStore.upsert(changedTextData)
        }
    }

    companion object {
        const val EXAMPLE_KEY = "APP_TEST"
    }
}
