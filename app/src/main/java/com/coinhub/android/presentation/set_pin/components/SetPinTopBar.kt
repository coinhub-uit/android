package com.coinhub.android.presentation.set_pin.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPinTopBar(onBack: (() -> Unit)?) {
    if (onBack != null) {
        TopAppBar(
            title = {
                Text("Change PIN", maxLines = 1)
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
            }
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Create PIN",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    CoinhubTheme {
        SetPinTopBar(onBack = {})
    }
}