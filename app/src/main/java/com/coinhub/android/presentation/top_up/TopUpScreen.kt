package com.coinhub.android.presentation.top_up

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopUpScreen(
    navigateToTopUpResult: () -> Unit,
) {
  Column {
      Text("Top Up Screen")
      Button(
        onClick = navigateToTopUpResult,
      ) {
          Text("Goto Top Up Result")
      }
  }
}