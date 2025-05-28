package com.coinhub.android.presentation.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.coinhub.android.presentation.navigation.Home
import com.coinhub.android.presentation.navigation.Settings
import com.coinhub.android.presentation.navigation.Tickets

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
}

data class BottomNavItem<T : Any>(
    val label: String,
    val route: T,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

val items = listOf(
    BottomNavItem("Home", Home, Icons.Outlined.Home, Icons.Default.Home),
    BottomNavItem("Tickets", Tickets, Icons.Outlined.Wallet, Icons.Default.Wallet),
    BottomNavItem("Settings", Settings, Icons.Outlined.Settings, Icons.Default.Settings)
)
