package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.MyRepository
import domain.ExampleOperationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel(
    private val repository: MyRepository,
    private val exampleOperationUseCase: ExampleOperationUseCase
): ViewModel() {

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

    fun getHelloWorld(): String {
       return repository.helloWorld()
    }

    fun getTextString() {
        viewModelScope.launch {
           val result =  exampleOperationUseCase.getExampleProcessText()
            _state.update {
                it.copy(
                    exampleNetText = result
                )
            }
        }
    }

    fun getLocalTextString() {
        viewModelScope.launch {
            val result = exampleOperationUseCase.getExampleLocalText()
            _state.update {
                it.copy(
                    exampleLocalText = result
                )
            }
        }
    }

    fun changeText() {
        viewModelScope.launch {
            exampleOperationUseCase.changeText()
        }
    }
}