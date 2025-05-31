package com.coinhub.android.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.presentation.menu.components.MenuAvatar
import com.coinhub.android.presentation.menu.components.MenuItem
import com.coinhub.android.presentation.menu.components.MenuTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@Composable
fun MenuScreen(
    onEditProfile: () -> Unit,
    onSettings: () -> Unit,
    onCredentialChange: () -> Unit,
    onSignOut: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val userModel = viewModel.userModel.collectAsStateWithLifecycle().value

    MenuScreen(
        onEditProfile = onEditProfile,
        onSettings = onSettings,
        onCredentialChange = onCredentialChange,
        onSignOut = onSignOut,
        userModel = userModel
    )
}

@Composable
private fun MenuScreen(
    onEditProfile: () -> Unit,
    onSettings: () -> Unit,
    onSignOut: () -> Unit,
    onCredentialChange: () -> Unit,
    userModel: UserModel,
) {
    val menuItems = listOf(
        MenuEntry(
            icon = Icons.Default.Edit,
            text = "Edit Profile",
            onClick = onEditProfile
        ),
        MenuEntry(
            icon = Icons.Default.Password,
            text = "Credentials",
            onClick = onCredentialChange
        ),
        MenuEntry(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = onSettings
        ),
    )
    Scaffold(
        topBar = { MenuTopBar(onSignOut = onSignOut) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar
            MenuAvatar(userModel = userModel)

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Menu items
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(menuItems) { item ->
                    MenuItem(
                        icon = item.icon,
                        text = item.text,
                        onClick = item.onClick
                    )
                }
            }
        }
    }
}

private data class MenuEntry(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit,
)

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun MenuScreenPreview() {
    CoinhubTheme {
        MenuScreen(
            onEditProfile = {},
            onSettings = {},
            onCredentialChange = {},
            onSignOut = {},
        )
    }
}