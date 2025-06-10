package com.coinhub.android.presentation.ticket_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.TicketModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun TicketDetailChart(ticket: TicketModel) {
    if (ticket.ticketHistories.isEmpty()) {
        return
    }

    val interests = ticket.ticketHistories.map { it.interest.toDouble() }

    val firstGradientColor = MaterialTheme.colorScheme.primary
    val secondGradientColor = MaterialTheme.colorScheme.onPrimary
    val lineData = remember(interests, firstGradientColor) {
        listOf(
            Line(
                label = "History",
                values = interests,
                color = SolidColor(firstGradientColor),
                firstGradientFillColor = firstGradientColor.copy(alpha = .5f),
                secondGradientFillColor = secondGradientColor,
            )
        )
    }

    Column {
        Text(
            text = "History", style = MaterialTheme.typography.titleMedium
        )
        LineChart(
            modifier = Modifier.height(256.dp).padding(horizontal = 22.dp),
            data = lineData,
        )
    }
}