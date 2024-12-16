package com.example.core.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

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
