package com.coinhub.android.presentation.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationScreen() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text("Notification Screen")
        }
    }
}