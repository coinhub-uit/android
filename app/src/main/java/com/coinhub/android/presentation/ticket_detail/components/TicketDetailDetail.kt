package com.coinhub.android.presentation.ticket_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.utils.toPercentFormat
import com.coinhub.android.utils.toVndFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.temporal.ChronoUnit

@Composable
fun TicketDetailDetail(ticket: TicketModel, withdrawPlan: AvailablePlanModel) {
    val firstHistory = ticket.ticketHistories.first()
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    // Calculate days passed for interest calculation
    val issuedAt = firstHistory.issuedAt
    val daysPassed = ChronoUnit.DAYS.between(
        issuedAt, java.time.LocalDate.of(
            currentDate.year, currentDate.monthNumber, currentDate.dayOfMonth
        )
    )

    // Calculate current interest using the formula
    val principal = BigDecimal(firstHistory.principal)
    val daysInYear = BigDecimal(365)
    val currentInterest =
        principal.multiply((withdrawPlan.rate/100).toBigDecimal()).multiply(BigDecimal(daysPassed))
            .divide(daysInYear, 2, RoundingMode.HALF_UP)

    val originalInterest = BigDecimal(firstHistory.interest)
    val maturityDays = ChronoUnit.DAYS.between(
        issuedAt, firstHistory.maturedAt
    )
    val annualRate = originalInterest.multiply(BigDecimal(365))
        .divide(principal.multiply(BigDecimal(maturityDays)), 4, RoundingMode.HALF_UP)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Detail", style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Maturity interest:", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = firstHistory.interest.toVndFormat(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Anticipated interest:", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = currentInterest.toVndFormat(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Days passed:", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "$daysPassed days", style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Rate:", style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = annualRate.toPercentFormat(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}