package com.coinhub.android.presentation.vault.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.math.BigInteger
import java.text.NumberFormat
import java.util.Locale

@Composable
fun VaultTickets(
    ticketModels: List<TicketModel>,
    currentDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    onTicketDetail: (Int) -> Unit,
) {
    Text(
        text = "Your Tickets", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(ticketModels) { ticketModel ->
            TicketItem(
                ticketModel = ticketModel,
                currentDate = currentDate,
                onTicketClick = { onTicketDetail(ticketModel.id) })
        }
    }
}

@Composable
private fun TicketItem(
    ticketModel: TicketModel,
    currentDate: LocalDate,
    onTicketClick: () -> Unit,
) {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    val firstHistory = ticketModel.ticketHistories.first()

    // Calculate progress using the provided currentDate
    val issuedAt = firstHistory.issuedAt
    val maturedAt = firstHistory.maturedAt

    // Calculate days passed and total days
    val totalDays = (maturedAt.toEpochDay() - issuedAt.toEpochDay()).toFloat().coerceAtLeast(1f)
    val daysPassed = (currentDate.toEpochDays() - issuedAt.toEpochDay()).toFloat().coerceAtLeast(0f)

    // Calculate progress as percentage
    val progress = (daysPassed / totalDays).coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth(), onClick = onTicketClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ticket #${ticketModel.id}", style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Method: ${ticketModel.method.description}", style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Progress timeline section
            Column(modifier = Modifier.fillMaxWidth()) {
                // Date labels above progress bar
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Issued: ${firstHistory.issuedAt}", style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Matures: ${firstHistory.maturedAt}", style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Progress bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Principal and Interest row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Principal on left
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Principal", style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = formatter.format(firstHistory.principal),
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
                        text = formatter.format(firstHistory.interest),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(widthDp = PreviewDeviceSpecs.WIDTH)
@Composable
private fun Preview() {
    Surface {
        CoinhubTheme {
            val sampleTickets = listOf(
                TicketModel(
                    id = 1,
                    openedAt = "01/01/2025".toLocalDate(),
                    closedAt = null,
                    status = TicketStatus.ACTIVE,
                    method = MethodEnum.NR,
                    ticketHistories = listOf(
                        TicketHistoryModel(
                            issuedAt = "01-01-2025".toLocalDate(),
                            maturedAt = "01-03-2025".toLocalDate(),
                            principal = BigInteger("1000000"),
                            interest = BigInteger("50000")
                        )
                    ),
                    plan = PlanModel(
                        id = 2, days = 90
                    )
                ), TicketModel(
                    id = 2,
                    openedAt = "02-01-2025".toLocalDate(),
                    closedAt = null,
                    status = TicketStatus.ACTIVE,
                    method = MethodEnum.PR,
                    ticketHistories = listOf(
                        TicketHistoryModel(
                            issuedAt = "02-01-2025".toLocalDate(),
                            maturedAt = "02-02-2025".toLocalDate(),
                            principal = BigInteger("1000000"),
                            interest = BigInteger("50000")
                        )
                    ),
                    plan = PlanModel(
                        id = 3, days = 60
                    )
                ), TicketModel(
                    id = 3,
                    openedAt = "01-01-2025".toLocalDate(),
                    closedAt = null,
                    status = TicketStatus.ACTIVE,
                    method = MethodEnum.PIR,
                    ticketHistories = listOf(
                        TicketHistoryModel(
                            issuedAt = "02-02-2025".toLocalDate(),
                            maturedAt = "02-03-2025".toLocalDate(),
                            principal = BigInteger("1000000"),
                            interest = BigInteger("50000")
                        ), TicketHistoryModel(
                            issuedAt = "01-01-2025".toLocalDate(),
                            maturedAt = "01-02-2025".toLocalDate(),
                            principal = BigInteger("1000000"),
                            interest = BigInteger("50000")
                        )
                    ),
                    plan = PlanModel(
                        id = 3, days = 30
                    )
                )
            )

            VaultTickets(
                ticketModels = sampleTickets, currentDate = LocalDate.parse("2025-01-31"), onTicketDetail = {})
        }
    }
}
