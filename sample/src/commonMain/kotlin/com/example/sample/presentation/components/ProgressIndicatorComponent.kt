package com.example.sample.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.filesystem.presentation.UploadState

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