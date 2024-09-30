package example.domain

import example.data.local.ExampleLocalRepository
import example.data.remote.ExampleHttpRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import utils.getOrThrow
import utils.onError
import utils.onSuccess

@Single
class ExampleOperationUseCase(
    private val exampleHttpRepository: ExampleHttpRepository,
    private val exampleLocalRepository: ExampleLocalRepository
) {

    val exampleUseCaseFlow: Flow<String> = exampleLocalRepository.exampleTextFlow

    suspend fun getExampleProcessText(): String {
        return exampleHttpRepository.getExampleText().onSuccess {
            Napier.d(
                tag = "ExampleOperationUseCase",
                message = "getExampleProcessText: successfully with $it"
            )
        }.onError {
            Napier.e(
                tag = "ExampleOperationUseCase",
                message = "getExampleProcessText: failed with $it"
            )
        }.getOrThrow()
    }

    suspend fun getExampleLocalText(): String {
        return exampleLocalRepository.getLocalData().onSuccess {
            Napier.d(
                tag = "ExampleOperationUseCase",
                message = "getExampleLocalText: successfully with $it"
            )
        }.onError {
            Napier.e(
                tag = "ExampleOperationUseCase",
                message = "getExampleLocalText: failed with $it"
            )
        }.getOrThrow()
    }

    suspend fun changeText() {
        return exampleLocalRepository.changeTheLocalData().onSuccess {
            Napier.d(
                tag = "ExampleOperationUseCase",
                message = "changeText: successfully with $it"
            )
        }.onError {
            Napier.e(
                tag = "ExampleOperationUseCase",
                message = "changeText: failed with $it"
            )
        }.getOrThrow()
    }
}