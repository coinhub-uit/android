package com.coinhub.android.presentation.top_up_result

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.coinhub.android.data.dtos.CreateTopUpDto

@Composable
fun TopUpResultScreen(onMain: () -> Unit, createTopUpDto: CreateTopUpDto) {
    Button(onClick = onMain) {
        Text("Navigate to Main Screen")
    }
}