package com.coinhub.android.presentation.top_up_result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.TopUpStatusEnum
import com.coinhub.android.ui.theme.CoinhubTheme

private data class TopUpResultStatusContent(
    val topUpStatus: TopUpStatusEnum,
    val icon: ImageVector,
    val text: String,
    val description: String,
    val color: Color,
)

@Composable
fun TopUpResultStatus(topUpStatus: TopUpStatusEnum) {
    val status = when (topUpStatus) {
        TopUpStatusEnum.PROCESSING -> TopUpResultStatusContent(
            topUpStatus = topUpStatus,
            icon = Icons.Filled.Pending,
            text = "Processing",
            description = "Your top-up is being processed. Please wait for confirmation.",
            color = MaterialTheme.colorScheme.inverseSurface,
        )

        TopUpStatusEnum.SUCCESS -> TopUpResultStatusContent(
            topUpStatus = topUpStatus,
            icon = Icons.Filled.CheckCircle,
            text = "Successful",
            description = "Your top-up was successful.",
            color = MaterialTheme.colorScheme.inversePrimary,
        )

        TopUpStatusEnum.DECLINED -> TopUpResultStatusContent(
            topUpStatus = topUpStatus,
            icon = Icons.Filled.Error,
            text = "Declined",
            description = "Your top-up was declined. Please check your payment details or contact support.",
            color = MaterialTheme.colorScheme.error,
        )

        TopUpStatusEnum.OVERDUE -> TopUpResultStatusContent(
            topUpStatus = topUpStatus,
            icon = Icons.Filled.TimerOff,
            text = "Overdue",
            description = "Your top-up is overdue. Please try again or contact support.",
            color = MaterialTheme.colorScheme.errorContainer,
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = status.icon,
            contentDescription = status.text,
            tint = status.color,
            modifier = Modifier.size(128.dp).padding(bottom = 8.dp)
        )
        Text(
            text = status.text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = status.description,
        )
    }
}

@PreviewLightDark
@Composable
fun TopUpResultStatusSuccessPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(topUpStatus = TopUpStatusEnum.SUCCESS)
        }
    }
}

@PreviewLightDark
@Composable
fun TopUpResultStatusProcessingPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(topUpStatus = TopUpStatusEnum.PROCESSING)
        }
    }
}

@PreviewLightDark
@Composable
fun TopUpResultStatusDeclinedPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(topUpStatus = TopUpStatusEnum.DECLINED)
        }
    }
}

@PreviewLightDark
@Composable
fun TopUpResultStatusOverduePreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(topUpStatus = TopUpStatusEnum.OVERDUE)
        }
    }
}