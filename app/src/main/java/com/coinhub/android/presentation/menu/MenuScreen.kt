package com.coinhub.android.presentation.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.coinhub.android.presentation.menu.components.MenuTopBar

@Composable
fun MenuScreen(
    navigateToEditProfile: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    Scaffold(
        topBar = {
            MenuTopBar()
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
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
}