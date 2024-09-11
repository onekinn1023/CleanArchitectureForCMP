package fileSystem.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun FileUploadScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<FileSystemViewModel>()
    val uploadState = viewModel.uploadState
    LinearProgressIndicator(
        progress = uploadState.progress ,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(16.dp)
    )
}