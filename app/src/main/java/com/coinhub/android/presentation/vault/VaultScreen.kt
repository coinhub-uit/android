package com.coinhub.android.presentation.vault

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun VaultScreen(
    navigateToCreateTicket: () -> Unit,
    navigateToTicketDetail: () -> Unit,
) {
    Column {
        Text("This is Vault Screen")
        Button(
            onClick = navigateToCreateTicket
        ) {
            Text("Create Ticket")
        }
        Button(
            onClick = navigateToTicketDetail
        ) {
            Text("Ticket Detail")
        }
    }
}
