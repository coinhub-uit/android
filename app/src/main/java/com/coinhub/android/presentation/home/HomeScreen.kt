package com.coinhub.android.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun HomeScreen(
    navigateToCreateSource: () -> Unit,
    navigateToSourceDetail: () -> Unit,
    navigateToTopUp: () -> Unit,
) {
    Column {
        Text("This is Home Screen")
        Button(
            onClick = navigateToCreateSource,
        ) {
            Text("Create Source")
        }
        Button(
            onClick = navigateToSourceDetail,
        ) {
            Text("Source Detail")
        }
        Button(
            onClick = navigateToTopUp,
        ) {
            Text("Top Up")
        }
    }
}