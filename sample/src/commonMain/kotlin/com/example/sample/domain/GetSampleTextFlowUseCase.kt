package com.example.sample.domain

import com.example.core.common.UseCaseFlowInteractor
import com.example.sample.data.MyExampleRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import org.koin.core.annotation.Single

@Single
class GetSampleTextFlowUseCase(
    private val exampleLocalRepository: MyExampleRepository
) : UseCaseFlowInteractor<Unit, String> {
    override fun invoke(i: Unit): Flow<String> {
        return exampleLocalRepository.sampleTextFlow.onStart {
            Napier.d(
                tag = "GetSampleTextFlowUseCase",
                message = "Start collecting..."
            )
        }
    }
}