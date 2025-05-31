package com.coinhub.android.presentation.top_up_result

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.coinhub.android.data.dtos.CreateTopUpDto
import com.coinhub.android.presentation.navigation.AppNavDestinations

@Composable
fun TopUpResultScreen(onMain: () -> Unit, topUp: AppNavDestinations.TopUpResult) {
    Button(onClick = onMain) {
        Text("Navigate to Main Screen")
    }
}