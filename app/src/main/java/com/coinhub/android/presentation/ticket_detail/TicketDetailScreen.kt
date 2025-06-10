package com.coinhub.android.presentation.ticket_detail

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.PlanModel
import com.coinhub.android.domain.models.TicketHistoryModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.TicketStatus
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.presentation.ticket_detail.components.TicketDetailChart
import com.coinhub.android.presentation.ticket_detail.components.TicketDetailDetail
import com.coinhub.android.presentation.ticket_detail.components.TicketDetailHeader
import com.coinhub.android.presentation.ticket_detail.components.TicketDetailProgressCard
import com.coinhub.android.presentation.ticket_detail.components.TicketDetailTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toLocalDate
import java.math.BigInteger

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TicketDetailScreen(
    ticketId: Int, onBack: () -> Unit,
    viewModel: TicketDetailViewModel = hiltViewModel(),
) {
    val ticket = viewModel.ticket.collectAsStateWithLifecycle().value
    val withdrawPlan = viewModel.withdrawPlan.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    LaunchedEffect(ticketId) {
        viewModel.getTicketAndWithdrawPlan(ticketId)
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    with(sharedTransitionScope) {
        TicketDetailScreen(
            ticket = ticket,
            withdrawPlan = withdrawPlan,
            isLoading = isLoading,
            onBack = onBack,
            onWithdraw = { viewModel.withdrawTicket(onBack) },
            modifier = Modifier.sharedBounds(
                animatedVisibilityScope = animatedVisibilityScope, sharedContentState = rememberSharedContentState(
                    key = "ticketDetail-$ticketId",
                )
            )
        )
    }
}

@Composable
fun TicketDetailScreen(
    modifier: Modifier = Modifier,
    ticket: TicketModel?,
    withdrawPlan: AvailablePlanModel?,
    isLoading: Boolean,
    onBack: () -> Unit,
    onWithdraw: () -> Unit,
) {
    Scaffold(
        topBar = {
            TicketDetailTopBar(onBack = onBack)
        },
        modifier = modifier,
    ) { innerPadding ->
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            return@Scaffold
        }

        if (ticket == null || withdrawPlan == null) {
            Text(
                text = "Ticket not found", modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            TicketDetailHeader(ticket = ticket)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            TicketDetailProgressCard(ticketModel = ticket)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            TicketDetailChart(ticket = ticket)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            TicketDetailDetail(ticket = ticket, withdrawPlan = withdrawPlan)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = {
                    onWithdraw()
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                Text(text = "Withdraw Now")
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun TicketDetailScreenPreview() {
    Surface {
        CoinhubTheme {
            TicketDetailScreen(
                ticket = TicketModel(
                id = 1,
                openedAt = "01/01/2025".toLocalDate(),
                closedAt = null,
                status = TicketStatus.ACTIVE,
                method = MethodEnum.PR,
                ticketHistories = listOf(
                    TicketHistoryModel(
                        issuedAt = "01/09/2025".toLocalDate(),
                        maturedAt = "01/11/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("40000")
                    ), TicketHistoryModel(
                        issuedAt = "01/07/2025".toLocalDate(),
                        maturedAt = "01/09/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("90000")
                    ), TicketHistoryModel(
                        issuedAt = "01/05/2025".toLocalDate(),
                        maturedAt = "01/07/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("23000")
                    ), TicketHistoryModel(
                        issuedAt = "01/03/2025".toLocalDate(),
                        maturedAt = "01/05/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("54000")
                    ), TicketHistoryModel(
                        issuedAt = "01/01/2025".toLocalDate(),
                        maturedAt = "01/03/2025".toLocalDate(),
                        principal = BigInteger("1000000"),
                        interest = BigInteger("50000")
                    )
                ),
                plan = PlanModel(
                    id = 2, days = 90
                )
            ), withdrawPlan = AvailablePlanModel(
                planHistoryId = 1, rate = 0.04f, planId = 2, days = 90
            ), onBack = {}, onWithdraw = {}, isLoading = false
            )
        }
    }
}
