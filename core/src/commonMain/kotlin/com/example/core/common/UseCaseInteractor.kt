package com.example.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

interface DomainError : CommonError

interface UseCaseResultInteractor<in I, out O, DE : DomainError> {
    suspend operator fun invoke(i: I): Result<O, DE>

    fun <T, E : CommonError> Result<T, E>.onDomainError(
        transformError: (E) -> DE
    ): Result<T, DE> {
        return when (this) {
            is Result.Error -> {
                Result.Error(transformError(this.error))
            }

            is Result.Success -> {
                this
            }
        }
    }
}

interface UseCaseFlowInteractor<in I, out O> {
    operator fun invoke(i: I): Flow<O>
}

private fun <I, O> UseCaseFlowInteractor<I, O>.stateIn(
    coroutineScope: CoroutineScope,
    input: I,
    defaultOut: O
): StateFlow<O> {
    return invoke(input).stateIn(coroutineScope, SharingStarted.Eagerly, defaultOut)
}

