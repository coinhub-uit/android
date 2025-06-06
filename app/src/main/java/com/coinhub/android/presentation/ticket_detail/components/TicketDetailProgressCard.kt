package com.coinhub.android.presentation.ticket_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.presentation.common.utils.calculateTicketProgress
import com.coinhub.android.utils.toDateString
import com.coinhub.android.utils.toVndFormat

@Composable
fun TicketDetailProgressCard(ticketModel: TicketModel) {
    val latestHistory = ticketModel.ticketHistories.firstOrNull() ?: return
    val progress = calculateTicketProgress(ticketHistory = latestHistory)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Progress", style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date labels above progress bar
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Issued: ${latestHistory.issuedAt.toDateString()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Matures: ${latestHistory.maturedAt.toDateString()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Principal and Interest row
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Principal on left
                Column {
                    Text(
                        text = "Principal", style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = latestHistory.principal.toVndFormat(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Interest on right
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Interest", style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = latestHistory.interest.toVndFormat(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}