package com.coinhub.android.presentation.source_detail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceDetailTopBar(
    onBack: () -> Unit,
    onClose: () -> Unit,
    showCloseDialog: Boolean,
    showBalanceErrorDialog: Boolean,
    onCloseConfirm: () -> Unit,
    dismissCloseDialog: () -> Unit,
    dismissBalanceErrorDialog: () -> Unit,
) {
    TopAppBar(
        title = {
            Text("Source Details", maxLines = 1)
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Close Source"
                )
            }
        }
    )

    // Close confirmation dialog
    if (showCloseDialog) {
        AlertDialog(
            onDismissRequest = dismissCloseDialog,
            title = { Text("Close Source") },
            text = { Text("Are you sure you want to close this source? This action cannot be undone.") },
            confirmButton = {
                Button(onClick = onCloseConfirm) {
                    Text("OK")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = dismissCloseDialog) {
                    Text("Cancel")
                }
            })
    }

    // Balance error dialog
    if (showBalanceErrorDialog) {
        AlertDialog(
            onDismissRequest = dismissBalanceErrorDialog,
            title = { Text("Cannot Close Source") },
            text = { Text("You cannot Close this source while its balance is not zero.") },
            confirmButton = {
                TextButton(onClick = dismissBalanceErrorDialog) {
                    Text("OK")
                }
            })
    }
}

@Preview(widthDp = PreviewDeviceSpecs.WIDTH)
@Composable
fun SourceDetailTopBarPreview() {
    CoinhubTheme {
        SourceDetailTopBar(
            onBack = {},
            onClose = {},
            showCloseDialog = false,
            showBalanceErrorDialog = false,
            onCloseConfirm = {},
            dismissCloseDialog = {},
            dismissBalanceErrorDialog = {}
        )
    }
}
