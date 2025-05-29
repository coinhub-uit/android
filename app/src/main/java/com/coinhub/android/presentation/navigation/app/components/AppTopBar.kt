package com.coinhub.android.presentation.navigation.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.coinhub.android.presentation.navigation.AppNavDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavHostController) {
    TopAppBar(title = {
        Text("CoinHub", maxLines = 1)
    }, actions = {
        IconButton(
            onClick = {
                navController.navigate(AppNavDestinations.Notification)
            }) {
            // TODO: May add badge count here
            Icon(Icons.Default.Notifications, "Notifications")
        }
        IconButton(
            onClick = {
                navController.navigate(AppNavDestinations.AiChat)
            }) {
            Icon(Icons.AutoMirrored.Default.Message, "AI Chat")
        }
    })
}