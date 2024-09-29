package example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import example.data.MyRepository
import example.domain.ExampleOperationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
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
        _state,
    ) { str, state ->
        state.copy(
            exampleLocalText = str,
        )
    }.onStart {
        /** When it comes to loading initial data here
         *  a. 如果在screen中请求加载初始化数据会存在页面重组导致频繁请求比如页面旋转
         *  b. 如果在viewModel的init初始化会导致进行Test时不可控
         *  c. 因此可以利用flow的onStart
         * */
        onEvent(MyEvent.GetHelloWorld)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L), //只有默认设置5s才可以避免屏幕旋转时重新收集flow从而达到即时旋转状态也能稳定
        MyState()
    )

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

    private suspend fun getHelloWorld() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val default =  repository.helloWorld()
        delay(3000L)
        _state.update {
            it.copy(
                initialText = default,
                isLoading = false
            )
        }
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
    data object ClickNavigateButton : MyEvent
}