package com.coinhub.android.presentation.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun MenuScreen(
    navigateToEditProfile: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    Column {
        Text("This is Menu Screen")
        Button(
            onClick = navigateToEditProfile,
        ) {
            Text("Edit Profile")
        }
        Button(
            onClick = navigateToSettings,
        ) {
            Text("Settings")
        }
    }
}