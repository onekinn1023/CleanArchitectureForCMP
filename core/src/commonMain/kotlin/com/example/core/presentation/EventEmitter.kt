package com.example.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.core.utils.MessageDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

interface EventOwner<Event> {
    val event: Flow<Event>
}

class EventEmitter<Event> : EventOwner<Event> {

    private val _event = Channel<Event>()

    override val event: Flow<Event>
        get() = _event.receiveAsFlow()

    fun send(event: Event) {
        _event.trySend(event)
    }

    fun cleared() {
        _event.close()
    }
}
