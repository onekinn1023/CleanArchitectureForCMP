package com.example.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

abstract class PresentationDataStore<Action, State, Event>(
    initialState: () -> State
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState())
    val state: StateFlow<State> = _state.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialState()
    )

    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    fun send(event: Event) {
        _event.trySend(event)
    }

    abstract fun onAction(action: Action)

    fun setState(newState: (State) -> State) {
        _state.update {
            newState(it)
        }
    }
}
