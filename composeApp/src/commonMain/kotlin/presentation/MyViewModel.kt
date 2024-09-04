package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MyRepository
import domain.ExampleOperationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel(
    private val repository: MyRepository,
    private val exampleOperationUseCase: ExampleOperationUseCase
) : ViewModel() {

    private val _effectChannel = Channel<MyEffect>()
    val effect = _effectChannel.receiveAsFlow()

    private val _localStringFlow = exampleOperationUseCase.exampleUseCaseFlow

    private val _state = MutableStateFlow(MyState())

    val state = combine(
        _localStringFlow,
        _state
    ) { str, state ->
        state.copy(
            exampleLocalText = str
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, MyState())

    fun onEvent(event: MyEvent) {
        viewModelScope.launch {
            when (event) {
                MyEvent.ChangeText -> changeText()
                MyEvent.GetHelloWorld -> getHelloWorld()
                MyEvent.GetLocalString -> getLocalTextString()
                MyEvent.GetRemoteString -> getTextString()
                MyEvent.ClickNavigateButton -> {
                    _effectChannel.send(MyEffect.NavigateToB)
                }
            }
        }
    }

    private fun getHelloWorld(): String {
        return repository.helloWorld()
    }

    private suspend fun getTextString() {
        val result = exampleOperationUseCase.getExampleProcessText()
        _state.update {
            it.copy(
                exampleNetText = result
            )
        }
    }

    private suspend fun getLocalTextString() {
        val result = exampleOperationUseCase.getExampleLocalText()
        _state.update {
            it.copy(
                exampleLocalText = result
            )
        }
    }

    private suspend fun changeText() {
        exampleOperationUseCase.changeText()
    }
}

sealed class MyEffect {
    data object Effect1 : MyEffect()
    data object NavigateToB : MyEffect()
}

sealed interface MyEvent {
    data object GetHelloWorld : MyEvent
    data object GetLocalString : MyEvent
    data object GetRemoteString : MyEvent
    data object ChangeText : MyEvent
    data object ClickNavigateButton: MyEvent
}