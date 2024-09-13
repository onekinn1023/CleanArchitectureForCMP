package decompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ScreenB(modifier: Modifier = Modifier, screenBComponent: ScreenBComponent) {
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title by screenBComponent.uiState.subscribeAsState()
        Text("ScreenB-The args is $title")
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier,
            onClick = {
                screenBComponent.backToA()
            }
        ) {
            Text(text = "Back to A")
        }
        Button(
            modifier = Modifier,
            onClick = {
                screenBComponent.navigationToUploadFileScreen()
            }
        ) {
            Text(text = "Navigate to Upload File Screen")
        }
    }
}
