package com.coinhub.android.presentation.menu.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar() {
    CenterAlignedTopAppBar(title = {
        Text("Menu", maxLines = 1)
    })
}
