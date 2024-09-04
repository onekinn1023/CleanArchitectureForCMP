@file:OptIn(KoinExperimentalAPI::class)

package presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import decompose.MyScreenComponent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import utils.ObserveAsEvent

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    myScreenComponent: MyScreenComponent? = null,
    isDecomposeTheme: Boolean = false
) {
    val viewModel = koinViewModel<MyViewModel>()
    val state by viewModel.state.collectAsState()
    ObserveAsEvent(viewModel.effect) {
        when (it) {
            MyEffect.Effect1 -> {
                println("Effect 1")
            }

            MyEffect.NavigateToB -> {
                myScreenComponent?.onAction(
                    MyScreenComponent.MyScreenAction.NavigateToNext(
                        state.exampleLocalText
                    )
                )
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                viewModel.onEvent(MyEvent.GetRemoteString)
            }
        ) {
            Text(text = "Request the test api")
        }
        Text(text = state.exampleNetText)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.onEvent(MyEvent.GetLocalString)
            }
        ) {
            Text(text = "Request local text")
        }
        Text(text = state.exampleLocalText)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewModel.onEvent(MyEvent.ChangeText)
            }
        ) {
            Text(text = "Change local text")
        }
        if (isDecomposeTheme) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    viewModel.onEvent(MyEvent.ClickNavigateButton)
                }
            ) {
                Text(text = "Navigate to next screen B!")
            }
        }
    }
}