package com.coinhub.android.presentation.ticket_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.utils.toVndFormat

@Composable
fun TicketDetailHeader(ticket: TicketModel) {
    val latestHistory = ticket.ticketHistories.firstOrNull()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Ticket #${ticket.id}",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Method: ${ticket.method.description}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (latestHistory != null) {
            Column {
                Text(
                    text = "Principal", style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = latestHistory.principal.toVndFormat(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
