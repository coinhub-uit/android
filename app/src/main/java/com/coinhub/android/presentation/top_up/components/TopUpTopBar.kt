package com.coinhub.android.presentation.top_up.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpTopBar(navigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text("Top Up", maxLines = 1)
        },
        navigationIcon = {
            IconButton(
                onClick = navigateUp
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
        TopUpTopBar(navigateUp = {})
    }
}