package com.coinhub.android.presentation.ai_chat.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatTopBar(
    onBack: () -> Unit,
    onDeleteSession: () -> Unit,
) {
    TopAppBar(
        title = {
            Text("AI Chat", maxLines = 1)
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Back"
                )
            }
        },
        actions =  {
            IconButton(
                onClick = onDeleteSession,
            ) {
                Icon(
                    Icons.Default.Delete,
                    "Delete Session"
                )
            }
        }
    )
}

@Preview
@Composable
fun TopUpTopBarPreview() {
    CoinhubTheme {
        AiChatTopBar(
            onBack = {},
            onDeleteSession = {}
        )
    }
}