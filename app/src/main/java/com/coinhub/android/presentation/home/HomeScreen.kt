package com.coinhub.android.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.coinhub.android.presentation.home.components.HomeTopBar

@Composable
fun HomeScreen(
    navigateToCreateSource: () -> Unit,
    navigateToSourceDetail: () -> Unit,
    navigateToTopUp: () -> Unit,
    navigateToNotification: () -> Unit, navigateToAiChat: () -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                navigateToNotification = navigateToNotification,
                navigateToAiChat = navigateToAiChat
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Text("This is Home Screen")
            Button(
                onClick = navigateToCreateSource,
            ) {
                Text("Create Source")
            }
            Button(
                onClick = navigateToSourceDetail,
            ) {
                Text("Source Detail")
            }
            Button(
                onClick = navigateToTopUp,
            ) {
                Text("Top Up")
            }
        }
    }
}