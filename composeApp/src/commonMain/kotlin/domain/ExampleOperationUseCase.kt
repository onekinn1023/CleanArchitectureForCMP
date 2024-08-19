package domain

import data.local.ExampleLocalRepository
import data.remote.ExampleHttpRepository
import kotlinx.coroutines.flow.Flow
import utils.getOrThrow
import utils.onError
import utils.onSuccess

class ExampleOperationUseCase(
    private val exampleHttpRepository: ExampleHttpRepository,
    private val exampleLocalRepository: ExampleLocalRepository
) {

    val exampleUseCaseFlow: Flow<String> = exampleLocalRepository.exampleTextFlow

    suspend fun getExampleProcessText(): String {
       return exampleHttpRepository.getExampleText().onSuccess {
            println("getExampleProcessText: successfully with $it")
        }.onError {
            println("getExampleProcessText: failed with $it")
        }.getOrThrow()
    }

    suspend fun getExampleLocalText(): String {
        return exampleLocalRepository.getLocalData().onSuccess {
            println("getExampleLocalText: successfully with $it")
        }.onError {
            println("getExampleLocalText: failed with $it")
        }.getOrThrow()
    }

    suspend fun changeText(){
        return exampleLocalRepository.changeTheLocalData().onSuccess {
            println("changeText: successfully with $it")
        }.onError {
            println("changeText: failed with $it")
        }.getOrThrow()
    }
}