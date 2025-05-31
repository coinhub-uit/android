package com.coinhub.android.presentation.top_up_result

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TopUpResultScreen(onMain: () -> Unit) {
    Button(onClick = onMain) {
        Text("Navigate to Main Screen")
    }
}