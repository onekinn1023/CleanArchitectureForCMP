package com.example.sample.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.sample.presentation.viewmodels.PermissionViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RequirePermissionScreen(modifier: Modifier = Modifier, controller: PermissionsController) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel = koinViewModel<PermissionViewModel>()
        val state = viewModel.state.collectAsState()

        when (state.value) {
            PermissionState.Granted -> {
                Text("Record audio permission granted!")
            }

            PermissionState.DeniedAlways -> {
                Text("Permission was permanently declined.")
                Button(onClick = {
                    viewModel.openSettings(controller)
                }) {
                    Text("Open app settings")
                }
            }

            else -> {
                Button(
                    onClick = {
                        viewModel.provideOrRequestRecordAudioPermission(controller)
                    }
                ) {
                    Text("Request permission")
                }
            }
        }
    }
}