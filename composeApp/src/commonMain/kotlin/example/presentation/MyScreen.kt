
package example.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.common.ObserveAsEvent
import decompose.MyScreenComponent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

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
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            DemoScreen(
                modifier = Modifier.fillMaxSize(),
                state = state,
                onAction = viewModel::onEvent,
                isDecomposeTheme = isDecomposeTheme
            )
        }
    }

}

@Composable
fun DemoScreen(
    modifier: Modifier = Modifier,
    state: MyState,
    onAction: (MyEvent) -> Unit,
    isDecomposeTheme: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = state.initialText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(25.dp))
        DemoButtonText(
            onAction = { onAction(MyEvent.GetRemoteString) },
            hint = "Request the test api",
            text = state.exampleNetText
        )
        Spacer(modifier = Modifier.height(10.dp))
        DemoButtonText(
            onAction = { onAction(MyEvent.GetLocalString) },
            hint = "Request local text",
            text = state.exampleLocalText
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                onAction(MyEvent.ChangeText)
            }
        ) {
            Text(text = "Change local text")
        }
        if (isDecomposeTheme) {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onAction(MyEvent.ClickNavigateButton)
                }
            ) {
                Text(text = "Navigate to next screen B!")
            }
        }
    }
}

@Composable
fun DemoButtonText(
    onAction: () -> Unit = {},
    hint: String,
    text: String
) {
    Button(
        onClick = {
            onAction()
        }
    ) {
        Text(text = hint)
    }
    Text(text = text)
}