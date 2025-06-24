package com.coinhub.android.presentation.ticket_detail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TicketDetailWithdrawButton(
    modifier: Modifier = Modifier,
    onWithdraw: () -> Unit,
    isWithdrawing: Boolean,
    onBack: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            showDialog = true
        },
        enabled = !isWithdrawing,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Text(text = "Withdraw Now")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Withdraw") },
            text = { Text("Are you sure you want to withdraw? This action cannot be undone.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onWithdraw()
                        onBack()
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
