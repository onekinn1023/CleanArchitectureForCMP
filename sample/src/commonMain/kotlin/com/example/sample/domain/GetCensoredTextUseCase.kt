package com.example.sample.domain

import com.example.core.common.DomainError
import com.example.core.common.Result
import com.example.core.common.UseCaseResultInteractor
import com.example.core.common.map
import com.example.network.data.FileWordRepository
import com.example.network.domain.CensoredText
import io.github.aakira.napier.Napier
import org.koin.core.annotation.Single

@Single
class GetCensoredTextUseCase(
    private val fileWordRepository: FileWordRepository,
    ): UseCaseResultInteractor<String, String, GetCensoredTextUseCase.GetCensoredTextError> {
    override suspend fun invoke(i: String): Result<String, GetCensoredTextError> {
        val censoredText = CensoredText(
            result = i
        )
        return fileWordRepository.getCensoredText(censoredText).map {
            Napier.d(
                tag = "GetCensoredTextUseCase",
                message = "getExampleProcessText: successfully with $it"
            )
            it.result
        }.onDomainError {
            Napier.e(
                tag = "GetCensoredTextUseCase",
                message = "getExampleProcessText: failed with $it"
            )
             GetCensoredTextError.GENERAL("")
        }
    }

    sealed class GetCensoredTextError(errorMsg: String = ""): DomainError {
        data class GENERAL(val msg: String): GetCensoredTextError(msg)
    }
}

