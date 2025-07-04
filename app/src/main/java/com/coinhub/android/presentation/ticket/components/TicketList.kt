package com.coinhub.android.presentation.ticket.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.PlanModel
import com.coinhub.android.domain.models.TicketHistoryModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.TicketStatus
import com.coinhub.android.presentation.common.utils.calculateTicketProgress
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toLocalDate
import com.coinhub.android.utils.toVndFormat
import java.math.BigInteger
import java.time.LocalDate

@Composable
fun TicketList(
    modifier: Modifier = Modifier,
    tickets: List<TicketModel>,
    currentDate: LocalDate = LocalDate.now(),
    onTicketDetail: (Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (tickets.isEmpty()) {
            return@Column
        }
        Text(
            text = "Your Tickets",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 80.dp),
        ) {
            items(tickets, key = {
                it.id
            }) { ticket ->
                TicketItem(
                    ticket = ticket,
                    currentDate = currentDate,
                    onTicketClick = { onTicketDetail(ticket.id) })
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun TicketItem(
    ticket: TicketModel,
    currentDate: LocalDate,
    onTicketClick: () -> Unit,
) {
    val firstHistory = ticket.ticketHistories.first()
    val progress = calculateTicketProgress(firstHistory, currentDate)

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")


    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .sharedBounds(
                    animatedVisibilityScope = animatedVisibilityScope, sharedContentState = rememberSharedContentState(
                        key = "ticketDetail-${ticket.id}",
                    )
                ),
            onClick = onTicketClick,
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
                        text = "Ticket #${ticket.id}", style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Method: ${ticket.method.description}", style = MaterialTheme.typography.bodyMedium
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
                            text = firstHistory.principal.toVndFormat(),
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
                            text = firstHistory.interest.toVndFormat(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
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

            TicketList(
                tickets = sampleTickets, currentDate = LocalDate.parse("2025-01-31"), onTicketDetail = {})
        }
    }
}
