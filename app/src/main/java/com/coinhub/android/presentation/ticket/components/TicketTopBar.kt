package com.coinhub.android.presentation.ticket.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketTopBar() {
    CenterAlignedTopAppBar(title = {
        Text("Tickets", maxLines = 1)
    })
}
