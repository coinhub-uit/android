package com.coinhub.android.presentation.vault

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.PlanModel
import com.coinhub.android.data.models.TicketHistoryModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.TicketStatus
import com.coinhub.android.presentation.vault.components.VaultStatistics
import com.coinhub.android.presentation.vault.components.VaultTickets
import com.coinhub.android.presentation.vault.components.VaultTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toLocalDate
import java.math.BigInteger

@Composable
fun VaultScreen(
    onCreateTicket: () -> Unit,
    onTicketDetail: (Int) -> Unit,
    viewModel: VaultViewModel = hiltViewModel(),
) {
    val ticketModels by viewModel.ticketModels.collectAsStateWithLifecycle()
    val totalPrincipal by viewModel.totalPrincipal.collectAsStateWithLifecycle()
    val totalInterest by viewModel.totalInterest.collectAsStateWithLifecycle()

    VaultScreen(
        ticketModels = ticketModels,
        totalPrincipal = totalPrincipal,
        totalInterest = totalInterest,
        onCreateTicket = onCreateTicket,
        onTicketDetail = onTicketDetail
    )
}

@Composable
private fun VaultScreen(
    ticketModels: List<TicketModel>,
    totalPrincipal: BigInteger,
    totalInterest: BigInteger,
    onCreateTicket: () -> Unit,
    onTicketDetail: (Int) -> Unit,
) {
    Scaffold(modifier = Modifier.padding(16.dp), topBar = {
        VaultTopBar()
    }) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            VaultStatistics(
                totalPrincipal = totalPrincipal, totalInterest = totalInterest, onCreateTicket = onCreateTicket
            )

            Spacer(modifier = Modifier.height(24.dp))

            VaultTickets(ticketModels = ticketModels, onTicketDetail = onTicketDetail)
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun VaultScreenPreview() {
    CoinhubTheme {
        Surface {
            VaultScreen(
                ticketModels = listOf(
                    TicketModel(
                        id = 1,
                        openedAt = "01/01/2025".toLocalDate(),
                        closedAt = null,
                        status = TicketStatus.ACTIVE,
                        method = MethodEnum.NR,
                        ticketHistories = listOf(
                            TicketHistoryModel(
                                issuedAt = "01/01/2025".toLocalDate(),
                                maturedAt = "01/03/2025".toLocalDate(),
                                principal = BigInteger("1000000"),
                                interest = BigInteger("50000")
                            )
                        ),
                        plan = PlanModel(
                            id = 2, days = 90
                        )
                    ), TicketModel(
                        id = 2,
                        openedAt = "02/01/2025".toLocalDate(),
                        closedAt = null,
                        status = TicketStatus.ACTIVE,
                        method = MethodEnum.PR,
                        ticketHistories = listOf(
                            TicketHistoryModel(
                                issuedAt = "02/01/2025".toLocalDate(),
                                maturedAt = "02/02/2025".toLocalDate(),
                                principal = BigInteger("1000000"),
                                interest = BigInteger("50000")
                            )
                        ),
                        plan = PlanModel(
                            id = 3, days = 60
                        )
                    ), TicketModel(
                        id = 3,
                        openedAt = "01/01/2025".toLocalDate(),
                        closedAt = null,
                        status = TicketStatus.ACTIVE,
                        method = MethodEnum.PIR,
                        ticketHistories = listOf(
                            TicketHistoryModel(
                                issuedAt = "02/02/2025".toLocalDate(),
                                maturedAt = "02/03/2025".toLocalDate(),
                                principal = BigInteger("1000000"),
                                interest = BigInteger("50000")
                            ), TicketHistoryModel(
                                issuedAt = "01/01/2025".toLocalDate(),
                                maturedAt = "01/02/2025".toLocalDate(),
                                principal = BigInteger("1000000"),
                                interest = BigInteger("50000")
                            )
                        ),
                        plan = PlanModel(
                            id = 3, days = 30
                        )
                    )
                ),
                totalPrincipal = BigInteger("3000000"),
                totalInterest = BigInteger("130000"),
                onCreateTicket = {},
                onTicketDetail = {})
        }
    }
}
