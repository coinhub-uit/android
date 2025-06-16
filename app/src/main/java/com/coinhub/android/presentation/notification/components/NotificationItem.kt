package com.coinhub.android.presentation.notification.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.toDateString
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun NotificationItem(notification: NotificationModel, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(), onClick = {
            expanded = !expanded
        }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = if (expanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f).animateContentSize()
                )
                if (!notification.isRead) {
                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                            .clip(
                                CircleShape
                            )
                            .background(MaterialTheme.colorScheme.error)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (expanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = notification.createdAt.toDateString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun Preview() {
    CoinhubTheme {
        Surface {
            NotificationItem(
                notification = NotificationModel(
                    id = Uuid.NIL,
                    title = "Sample Notification",
                    body = "This is a sample notification body to demonstrate the layout.",
                    createdAt = ZonedDateTime.now(),
                    isRead = false
                )
            )
        }
    }
}