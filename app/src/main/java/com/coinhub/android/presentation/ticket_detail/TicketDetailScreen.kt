package com.coinhub.android.presentation.ticket_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.managers.TicketManager

@Composable
fun TicketDetailScreen(
    ticketId: Int, onBack: () -> Unit,
    viewmodel: TicketDetailViewModel = hiltViewModel(),
) {
    val ticketDetailState = viewmodel.ticketModelsState.collectAsStateWithLifecycle().value

    when (ticketDetailState) {
        is TicketManager.TicketModelsState.Error -> {} //TODO: Do this babe
        TicketManager.TicketModelsState.Loading -> {}
        is TicketManager.TicketModelsState.Success -> Text("This is the Ticket Detail Screen for ticket ID: $ticketId")
    }
}