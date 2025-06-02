package com.coinhub.android.presentation.create_ticket.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicketTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("Create Ticket", maxLines = 1)
        },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Back"
                )
            }
        }
    )
}

@Preview
@Composable
fun TopUpTopBarPreview(){
    CoinhubTheme {
        TopUpTopBar(onBack = {})
    }
}