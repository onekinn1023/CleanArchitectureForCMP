package com.example.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

interface CommonViewStore<Action, State> {

    val state: StateFlow<State>

    fun dispatch(action: Action)
}

abstract class PresentationViewDataStore<Action, State>(
    initialState: () -> State
) : ViewModel(), CommonViewStore<Action, State> {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState())
    override val state: StateFlow<State> = _state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialState()
    )

    private val _actions = MutableSharedFlow<Action>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val actions = _actions.asSharedFlow()

    fun setState(newState: (State) -> State) {
        _state.update {
            newState(it)
        }
    }

    override fun dispatch(action: Action) {
        _actions.tryEmit(action)
    }
}


interface EventViewStore<Action, State, Event> : CommonViewStore<Action, State>, EventOwner<Event> {
    fun send(event: Event)
}

abstract class PresentationEventViewDataStore<Action, State, Event>(
    initialState: () -> State
) : ViewModel(), EventViewStore<Action, State, Event> {

    private val _actions = MutableSharedFlow<Action>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val actions = _actions.asSharedFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState())
    override val state: StateFlow<State> = _state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialState()
    )

    private val emitter = EventEmitter<Event>()

    override val event = emitter.event

    override fun send(event: Event) {
        emitter.send(event)
    }

    override fun dispatch(action: Action) {
        _actions.tryEmit(action)
    }

    fun setState(newState: (State) -> State) {
        _state.update {
            newState(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        emitter.cleared()
    }
}
