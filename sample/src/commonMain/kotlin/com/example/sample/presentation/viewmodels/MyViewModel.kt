package com.example.sample.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.core.common.onError
import com.example.core.common.onSuccess
import com.example.core.presentation.PresentationEventViewDataStore
import com.example.sample.data.MyExampleRepository
import com.example.sample.domain.GetCensoredTextUseCase
import com.example.sample.domain.GetSampleTextFlowUseCase
import com.example.sample.domain.UpdateSampleTextUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MyViewModel(
    private val repository: MyExampleRepository,
    private val updateSampleTextUseCase: UpdateSampleTextUseCase,
    private val getSampleTextFlowUseCase: GetSampleTextFlowUseCase,
    private val getCensoredTextUseCase: GetCensoredTextUseCase
) : PresentationEventViewDataStore<MyAction, MyState, MyEvent>(
    initialState = { MyState() }
) {

    private var textFlowJob: Job? = null
    private var actionHandlerJob: Job? = null

    init {
        provideActionHandler()
    }

    val myState = state
        .onStart {
            /** When it comes to loading initial data here
             *  a. 如果在screen中请求加载初始化数据会存在页面重组导致频繁请求比如页面旋转
             *  b. 如果在viewModel的init初始化会导致进行Test时不可控
             *  c. 因此可以利用flow的onStart
             * */
            observeText()
            dispatch(MyAction.GetHelloWorld)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), //只有默认设置5s才可以避免屏幕旋转时重新收集flow从而达到即时旋转状态也能稳定
            state.value
        )

    private fun provideActionHandler() {
        actionHandlerJob?.cancel()
        actionHandlerJob = viewModelScope.launch {
            actions.collect { action ->
                when (action) {
                    MyAction.ChangeText -> updateSampleTextUseCase(Unit)
                    MyAction.GetHelloWorld -> getHelloWorld()
                    is MyAction.GetRemoteString -> queryCensorText(action.text)
                    MyAction.ClickNavigateButton -> {
                        send(MyEvent.NavigateToB)
                    }
                }
            }
        }
    }

    private fun observeText() {
        textFlowJob?.cancel()
        textFlowJob = getSampleTextFlowUseCase(Unit)
            .onEach { exampleText ->
                setState {
                    it.copy(
                        exampleLocalText = exampleText
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getHelloWorld() {
        setState {
            it.copy(
                isLoading = true
            )
        }
        val default = repository.helloWorld()
        delay(3000L)
        setState {
            it.copy(
                initialText = default,
                isLoading = false
            )
        }
    }

    private suspend fun queryCensorText(text: String) {
        getCensoredTextUseCase(text).onSuccess { result ->
            setState {
                it.copy(
                    exampleNetText = result
                )
            }
        }.onError {
            // Do some error msg handle to state
        }
    }
}

data class MyState(
    val initialText: String = "",
    val exampleNetText: String = "",
    val exampleLocalText: String = "",
    val isLoading: Boolean = false
)

sealed class MyEvent {
    data object FirstEvent : MyEvent()
    data object NavigateToB : MyEvent()
}

sealed interface MyAction {
    data object GetHelloWorld : MyAction
    data class GetRemoteString(val text: String) : MyAction
    data object ChangeText : MyAction
    data object ClickNavigateButton : MyAction
}