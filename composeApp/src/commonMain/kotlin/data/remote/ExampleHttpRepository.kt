package data.remote

import dataStore.remote.ExampleHttpDataSource
import utils.NetworkError
import utils.Result

interface ExampleHttpRepository {
    suspend fun getExampleText(): Result<String, NetworkError>
}

class ExampleHttpRepositoryImpl(
    private val exampleHttpDataSource: ExampleHttpDataSource
): ExampleHttpRepository {

    override suspend fun getExampleText(): Result<String, NetworkError> {
        return exampleHttpDataSource.censorWords("test")
    }
}