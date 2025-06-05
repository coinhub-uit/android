package com.coinhub.android.presentation.common.permission_requests

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    if (!permissionState.status.isGranted) {
        LaunchedEffect(Unit) {
            permissionState.launchPermissionRequest()
        }
    }
}
