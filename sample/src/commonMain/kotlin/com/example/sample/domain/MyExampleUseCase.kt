package com.example.sample.domain

import com.example.core.common.getOrThrow
import com.example.core.common.map
import com.example.core.common.onError
import com.example.core.common.onSuccess
import com.example.network.data.FileWordRepository
import com.example.network.domain.CensoredText
import com.example.sample.data.MyExampleRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class MyExampleOperationUseCase(
    private val exampleLocalRepository: MyExampleRepository,
    private val fileWordRepository: FileWordRepository,
) {

    val exampleUseCaseFlow: Flow<String> = exampleLocalRepository.exampleTextFlow

    suspend fun getExampleProcessText(text: String): String {
        val censoredText = CensoredText(
            result = text
        )
        return fileWordRepository.getCensoredText(censoredText).map {
            Napier.d(
                tag = "ExampleOperationUseCase",
                message = "getExampleProcessText: successfully with $it"
            )
            it.result
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