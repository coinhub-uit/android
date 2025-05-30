package com.coinhub.android.presentation.vault

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coinhub.android.presentation.vault.components.VaultTopBar

@Composable
fun VaultScreen(
    navigateToCreateTicket: () -> Unit,
    navigateToTicketDetail: () -> Unit,
) {
    Scaffold(
        topBar = {
            VaultTopBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
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

    }
}
