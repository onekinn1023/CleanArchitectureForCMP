package com.example.sample.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.PresentationViewDataStore
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PermissionViewModel : PresentationViewDataStore<Unit, PermissionState>(
    initialState = { PermissionState.NotDetermined }
) {
    fun provideOrRequestRecordAudioPermission(
        controller: PermissionsController
    ) {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.RECORD_AUDIO)
                setState { PermissionState.Granted }
            } catch (e: DeniedAlwaysException) {
                setState { PermissionState.DeniedAlways }
            } catch (e: DeniedException) {
                setState { PermissionState.Denied }
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun openSettings(
        controller: PermissionsController
    ) {
        viewModelScope.launch {
            controller.openAppSettings()
        }
    }
}