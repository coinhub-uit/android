package com.coinhub.android.presentation.vault.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultTopBar() {
    TopAppBar(title = {
        Text("CoinHub", maxLines = 1)
    })
}
