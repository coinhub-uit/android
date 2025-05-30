package com.coinhub.android.presentation.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navigateToNotification: () -> Unit, navigateToAiChat: () -> Unit) {
    TopAppBar(title = {
        Text("CoinHub", maxLines = 1)
    }, actions = {
        IconButton(
            onClick = navigateToNotification
        ) {
            // TODO: May add badge count here
            Icon(Icons.Outlined.Notifications, "Notifications")
        }
        IconButton(
            onClick = navigateToAiChat
        ) {
            Icon(Icons.AutoMirrored.Outlined.Message, "AI Chat")
        }
    })
}
