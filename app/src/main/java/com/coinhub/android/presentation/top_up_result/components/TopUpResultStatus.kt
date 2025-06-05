package com.coinhub.android.presentation.top_up_result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.toDateString
import com.coinhub.android.utils.toVndFormat
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private data class TopUpResultStatusContent(
    val topUpStatus: TopUpModel.StatusEnum,
    val icon: ImageVector,
    val text: String,
    val description: String,
    val color: Color,
)

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TopUpResultStatus(topUp: TopUpModel?) {
    if (topUp == null) return

    val statusContent = when (topUp.status) {
        TopUpModel.StatusEnum.proccesing -> TopUpResultStatusContent(
            topUpStatus = topUp.status,
            icon = Icons.Filled.Pending,
            text = "Processing",
            description = "Your top-up is being processed. Please wait for confirmation.",
            color = MaterialTheme.colorScheme.inverseSurface,
        )

        TopUpModel.StatusEnum.success -> TopUpResultStatusContent(
            topUpStatus = topUp.status,
            icon = Icons.Filled.CheckCircle,
            text = "Successful",
            description = "Your top-up was successful.",
            color = MaterialTheme.colorScheme.inversePrimary,
        )

        TopUpModel.StatusEnum.declined -> TopUpResultStatusContent(
            topUpStatus = topUp.status,
            icon = Icons.Filled.Error,
            text = "Declined",
            description = "Your top-up was declined. Please check your payment details or contact support.",
            color = MaterialTheme.colorScheme.error,
        )

        TopUpModel.StatusEnum.overdue -> TopUpResultStatusContent(
            topUpStatus = topUp.status,
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
            imageVector = statusContent.icon,
            contentDescription = statusContent.text,
            tint = statusContent.color,
            modifier = Modifier
                .size(128.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = statusContent.text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = statusContent.description,
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Provider:",
                modifier = Modifier.weight(1f),
            )
            Text(
                text = topUp.provider.displayName,
            )
        }
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = "Amount:",
                modifier = Modifier.weight(1f),
            )
            Text(
                text = topUp.amount.toVndFormat(),
            )
        }
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = "Created at:",
                modifier = Modifier.weight(1f),
            )
            Text(
                text = topUp.createdAt.toDateString(),
            )
        }
        Text(
            text = "ID: ${topUp.id}",
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun TopUpResultStatusSuccessPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(
                topUp = TopUpModel(
                    id = Uuid.random(),
                    provider = TopUpModel.ProviderEnum.vnpay,
                    amount = java.math.BigInteger("1000000"),
                    status = TopUpModel.StatusEnum.success,
                    createdAt = ZonedDateTime.now()
                )
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun TopUpResultStatusProcessingPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(
                topUp = TopUpModel(
                    id = Uuid.random(),
                    provider = TopUpModel.ProviderEnum.momo,
                    amount = java.math.BigInteger("500000"),
                    status = TopUpModel.StatusEnum.proccesing,
                    createdAt = java.time.ZonedDateTime.now()
                )
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun TopUpResultStatusDeclinedPreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(
                topUp = TopUpModel(
                    id = Uuid.random(),
                    provider = TopUpModel.ProviderEnum.zalo,
                    amount = java.math.BigInteger("200000"),
                    status = TopUpModel.StatusEnum.declined,
                    createdAt = java.time.ZonedDateTime.now()
                )
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun TopUpResultStatusOverduePreview() {
    CoinhubTheme {
        Surface {
            TopUpResultStatus(
                topUp = TopUpModel(
                    id = Uuid.random(),
                    provider = TopUpModel.ProviderEnum.vnpay,
                    amount = java.math.BigInteger("300000"),
                    status = TopUpModel.StatusEnum.overdue,
                    createdAt = java.time.ZonedDateTime.now()
                )
            )
        }
    }
}
