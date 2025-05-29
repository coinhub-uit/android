package com.coinhub.android.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.coinhub.android.presentation.common.permission_requests.RequestNotificationPermissionDialog
import com.coinhub.android.presentation.main.components.BottomBar
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.MainPagesNavGraph

@Composable
fun MainScreen(navController: NavHostController) {
    RequestNotificationPermissionDialog()
    Scaffold(bottomBar = {
        BottomBar(navController)
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(AppNavDestinations.AiChat) }) {
            Icon(Icons.AutoMirrored.Default.Message, "AI Chat")
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MainPagesNavGraph(navController = navController)
        }
    }
}
