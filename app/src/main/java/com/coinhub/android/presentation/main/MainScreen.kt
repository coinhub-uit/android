package com.coinhub.android.presentation.main

import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.coinhub.android.presentation.common.permission_requests.RequestNotificationPermissionDialog

@Composable
fun HomeScreen() {
    Text("This is HomeScreen")

    RequestNotificationPermissionDialog()
}
