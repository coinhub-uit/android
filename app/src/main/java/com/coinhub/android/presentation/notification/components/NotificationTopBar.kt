package com.coinhub.android.presentation.notification.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("Notification", maxLines = 1)
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Back"
                )
            }
        }
    )
}

@Preview
@Composable
fun TopUpTopBarPreview() {
    CoinhubTheme {
        TopUpTopBar(onBack = {})
    }
}