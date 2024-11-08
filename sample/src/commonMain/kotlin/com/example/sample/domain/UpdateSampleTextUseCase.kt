package com.example.sample.domain

import com.example.core.common.DomainError
import com.example.core.common.Result
import com.example.core.common.UseCaseResultInteractor
import com.example.core.common.map
import com.example.sample.data.MyExampleRepository
import io.github.aakira.napier.Napier
import org.koin.core.annotation.Single

@Single
class UpdateSampleTextUseCase(
    private val exampleLocalRepository: MyExampleRepository
) : UseCaseResultInteractor<Unit, Unit, UpdateSampleTextUseCase.CaseError> {
    override suspend fun invoke(i: Unit): Result<Unit, CaseError> {
        return exampleLocalRepository.changeTheLocalData().map {
            Napier.d(
                tag = "UpdateSampleTextUseCase",
                message = "changeText: successfully with $it"
            )
        }.onDomainError {
            Napier.e(
                tag = "UpdateSampleTextUseCase",
                message = "changeText: failed with $it"
            )
            CaseError.GENERAL
        }
    }

    enum class CaseError : DomainError {
        GENERAL
    }
}



