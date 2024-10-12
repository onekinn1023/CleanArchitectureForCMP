package example.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.common.ObserveAsEvent
import decompose.UploadFileScreenComponent
import fileSystem.presentation.FileOperationEffect
import fileSystem.presentation.FileOperationEvent
import fileSystem.presentation.FileSystemViewModel
import fileSystem.presentation.UploadState
import io.github.aakira.napier.Napier
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FileUploadScreen(
    modifier: Modifier = Modifier,
    uploadFileScreenComponent: UploadFileScreenComponent
) {
    val viewModel = koinViewModel<FileSystemViewModel>()
    val state = viewModel.uploadState
    val filePicker = rememberFilePickerLauncher { file ->
        file?.let {
            viewModel.onEvent(FileOperationEvent.UploadFileInfo(it))
        }
    }
    ObserveAsEvent(viewModel.effect) {
        when (it) {
            FileOperationEffect.SelectFile -> {
                filePicker.launch()
            }
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!state.isUploading) {
            SelectFileScreen(
                selectAction = {  viewModel.onEvent(FileOperationEvent.SelectFile) },
                backPressed = { uploadFileScreenComponent.backPressed() }
            )
        } else {
            ProgressIndicatorComponent(
                state = state
            )
        }
        if (state.errorMessage != null) {
            Napier.e {
                "Maybe we can show a toast"
            }
        }
    }
}


@Composable
fun SelectFileScreen(
    modifier: Modifier = Modifier,
    selectAction: () -> Unit = {},
    backPressed: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .background(Color.Transparent)
                .clip(
                    RoundedCornerShape(5.dp)
                ),
            onClick = {
                selectAction()
            }
        ) {
            Text(text = "Select a file")
        }
        Spacer(modifier =  Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .background(Color.Transparent)
                .clip(
                    RoundedCornerShape(5.dp)
                ),
            onClick = {
                backPressed()
            }
        ) {
            Text("Back to Screen B")
        }
    }
}

@Composable
fun ProgressIndicatorComponent(
    modifier: Modifier = Modifier,
    state: UploadState
) {
    LinearProgressIndicator(
        progress = state.progress,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(16.dp)
    )
}