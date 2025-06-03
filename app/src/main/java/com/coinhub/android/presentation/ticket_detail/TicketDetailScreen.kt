package com.coinhub.android.presentation.ticket_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TicketDetailScreen(
    ticketId: Int, onBack: () -> Unit,
    viewmodel: TicketDetailViewModel = hiltViewModel(),
) {
    Text("This is the Ticket Detail Screen for ticket ID: $ticketId")
}