package com.coinhub.android.presentation.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.NotificationModel
import com.coinhub.android.presentation.notification.components.NotificationItem
import com.coinhub.android.presentation.notification.components.NotificationTopBar
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val notifications = viewModel.notifications.collectAsStateWithLifecycle().value

    NotificationScreen(
        onBack = onBack, notifications = notifications
    )
}

@OptIn(ExperimentalUuidApi::class)
@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    notifications: List<NotificationModel>,
) {
    Scaffold(
        topBar = {
            NotificationTopBar(
                onBack = onBack
            )
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notifications, key = {
                it.id.toString()
            }) { notification ->
                NotificationItem(notification)
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun Preview() {
    NotificationScreen(
        onBack = {},
        notifications = listOf(
            NotificationModel(
                id = Uuid.parse("123e4567-e89b-12d3-a456-426614174000"),
                title = "Test Notification",
                body = "This is a test notification message.",
                createdAt = ZonedDateTime.now(),
                isRead = false
            ),
            NotificationModel(
                id = Uuid.parse("123e4567-e89b-12d3-a456-426614174001"),
                title = "Another Notification",
                body = "This is another test notification message.",
                createdAt = ZonedDateTime.now(),
                isRead = true
            )
        )
    )
}