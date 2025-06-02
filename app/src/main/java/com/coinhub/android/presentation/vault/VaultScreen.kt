package com.coinhub.android.presentation.vault

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import com.coinhub.android.presentation.vault.components.VaultTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import kotlinx.datetime.LocalDate
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun VaultScreen(
    onCreateTicket: () -> Unit,
    onTicketDetail: (String) -> Unit,
    viewModel: VaultViewModel = hiltViewModel(),
) {
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()
    val totalPrincipal by viewModel.totalPrincipal.collectAsStateWithLifecycle()
    val totalInterest by viewModel.totalInterest.collectAsStateWithLifecycle()
    val availablePlans by viewModel.availablePlans.collectAsStateWithLifecycle()

    VaultScreen(
        tickets = tickets,
        totalPrincipal = totalPrincipal,
        totalInterest = totalInterest,
        availablePlans = availablePlans,
        onCreateTicket = onCreateTicket,
        onTicketDetail = onTicketDetail
    )
}

@Composable
private fun VaultScreen(
    tickets: List<TicketModel>,
    totalPrincipal: Long,
    totalInterest: Long,
    availablePlans: Int,
    onCreateTicket: () -> Unit,
    onTicketDetail: (String) -> Unit,
) {
    val formatter = NumberFormat.getNumberInstance(Locale.US)

    Scaffold(modifier = Modifier.padding(16.dp), topBar = {
        VaultTopBar()
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = onCreateTicket,
        ) {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = "Create Ticket"
            )
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            // Tickets Statistics Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Money,
                            contentDescription = "Ticket Statistics",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "${formatter.format(totalPrincipal)} VNĐ",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = "Interest",
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(end = 4.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Text(
                                text = "Interest: ${formatter.format(totalInterest)} VNĐ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Your Tickets",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tickets) { ticket ->
                    TicketItem(
                        ticket = ticket, onTicketClick = { onTicketDetail(ticket.id.toString()) })
                }
            }
        }
    }
}

@Composable
private fun TicketItem(
    ticket: TicketModel,
    onTicketClick: () -> Unit,
) {
    val formatter = NumberFormat.getNumberInstance(Locale.US)
    val firstHistory = ticket.ticketHistories.firstOrNull()

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(), onClick = onTicketClick
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

                Text(
                    text = ticket.status.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = when (ticket.status) {
                        TicketStatus.ACTIVE -> MaterialTheme.colorScheme.primary
                        TicketStatus.EARLY_WITH_DRAWN -> MaterialTheme.colorScheme.error
                        TicketStatus.MATURED_WITH_DRAWN -> MaterialTheme.colorScheme.tertiary
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Method: ${ticket.method.name}", style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Opened: ${ticket.openedAt}", style = MaterialTheme.typography.bodySmall
            )

            ticket.closedAt?.let {
                Text(
                    text = "Closed: $it", style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            firstHistory?.let {
                Text(
                    text = "Principal: ${formatter.format(it.principal)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Interest: ${formatter.format(it.interest)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun VaultScreenPreview() {
    CoinhubTheme {
        Surface {
            VaultScreen(
                tickets = listOf(
                    TicketModel(
                        id = 2,
                        openedAt = LocalDate.parse("2023-01-01"),
                        closedAt = null,
                        status = TicketStatus.ACTIVE,
                        method = MethodEnum.NR,
                        ticketHistories = listOf(
                            TicketHistoryModel(
                                issuedAt = LocalDate.parse("2022-01-01"), maturedAt = null, principal = BigDecimal(
                                100000), interest = BigDecimal(100000)
                            )
                        )
                        , plan = PlanModel(
                            id = 1,
                            days = 10
                        )
                    ), TicketModel(
                        id = 2,
                        openedAt = LocalDate.parse("2023-02-01"),
                        closedAt = LocalDate.parse("2023-02-28"),
                        status = TicketStatus.MATURED_WITH_DRAWN,
                        method = MethodEnum.PIR,
                        ticketHistories = listOf(
                            TicketHistoryModel(
                                issuedAt = LocalDate.parse("2022-01-01"), maturedAt = null, principal = BigDecimal( 100000), interest = BigDecimal(100000)
                            )
                        ), plan = PlanModel(
                            id =2,
                            days = -1
                        )
                    )
                ),
                totalPrincipal = 3000000,
                totalInterest = 130000,
                availablePlans = 3,
                onCreateTicket = {},
                onTicketDetail = {})
        }
    }
}
