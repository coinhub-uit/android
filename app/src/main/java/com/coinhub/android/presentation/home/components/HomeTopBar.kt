package com.coinhub.android.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.R
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onNotification: () -> Unit, onAiChat: () -> Unit) {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.coinhub),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("CoinHub", maxLines = 1)
        }
    }, actions = {
        IconButton(
            onClick = onNotification
        ) {
            // TODO: May add badge count here
            Icon(Icons.Outlined.Notifications, "Notifications")
        }
        IconButton(
            onClick = onAiChat
        ) {
            Icon(Icons.AutoMirrored.Outlined.Message, "AI Chat")
        }
    })
}

@Preview
@Composable
fun HomeTopBarPreview() {
    CoinhubTheme {
        HomeTopBar(onNotification = {}, onAiChat = {})
    }
}