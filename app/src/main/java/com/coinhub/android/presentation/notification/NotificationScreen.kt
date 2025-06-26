package com.coinhub.android.presentation.notification

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.presentation.notification.components.NotificationItem
import com.coinhub.android.presentation.notification.components.NotificationTopBar
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),
) {
    val notifications = viewModel.notifications
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isRefreshing = viewModel.isRefreshing.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    NotificationScreen(
        onBack = onBack,
        isLoading = isLoading,
        refresh = viewModel::refresh,
        isRefreshing = isRefreshing,
        markAsRead = viewModel::markAsRead,
        notifications = notifications,
    )
}

@OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    onBack: () -> Unit,
    isLoading: Boolean,
    refresh: () -> Unit,
    isRefreshing: Boolean,
    markAsRead: (Uuid) -> Unit,
    notifications: List<NotificationModel>,
) {
    Scaffold(
        topBar = {
            NotificationTopBar(
                onBack = onBack
            )
        },
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = refresh,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    notifications,
                    key = {
                        it.id.toString()
                    },
                ) { notification ->
                    NotificationItem(notification = notification, markAsRead = markAsRead)
                }
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
        isLoading = false,
        refresh = {},
        isRefreshing = false,
        markAsRead = {},
        notifications = listOf(
            NotificationModel(
                id = Uuid.NIL,
                title = "Test Notification",
                body = "This is a test notification message.",
                createdAt = ZonedDateTime.now(),
                isRead = false
            ),
            NotificationModel(
                id = Uuid.NIL,
                title = "Another Notification",
                body = "This is another test notification message.",
                createdAt = ZonedDateTime.now(),
                isRead = true
            )
        )
    )
}
