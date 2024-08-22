package decompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ScreenA(modifier: Modifier = Modifier, screenAComponent: ScreenAComponent) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it }
        )
        Button(
            modifier = Modifier,
            onClick = { screenAComponent.onEvent(ScreenAComponent.Event.NavigateToScreenB(text)) }
        ) {
            Text("Jump to B!")
        }
    }
}

@Composable
fun ScreenB(modifier: Modifier = Modifier, screenBComponent: ScreenBComponent) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val title by screenBComponent.uiState.subscribeAsState()
        Text("The args is $title")
    }
}
