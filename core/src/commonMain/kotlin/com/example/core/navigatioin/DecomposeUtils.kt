package com.example.core.navigatioin

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.subscribe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

inline fun <reified T : Any> StateFlow<T>.asValue(
    lifecycle: Lifecycle,
    context: CoroutineContext = Dispatchers.Main.immediate,
): Value<T> =
    asValue(
        initialValue = value,
        lifecycle = lifecycle,
        context = context,
    )

inline fun <reified T : Any> Flow<T>.asValue(
    initialValue: T,
    lifecycle: Lifecycle,
    context: CoroutineContext = Dispatchers.Main.immediate,
): Value<T> {
    val value = MutableValue(initialValue)
    var scope: CoroutineScope? = null

    lifecycle.subscribe(
        onStart = {
            scope = CoroutineScope(context).apply {
                launch {
                    collect { value.value = it }
                }
            }
        },
        onStop = {
            scope?.cancel()
            scope = null
        }
    )

    return value
}