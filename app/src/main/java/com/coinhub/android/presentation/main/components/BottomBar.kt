package com.coinhub.android.presentation.main.components

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coinhub.android.presentation.navigation.NavigationDestinations
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavItems.forEach { bottomNavItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { navDestination ->
                    navDestination.hasRoute(bottomNavItem.route::class)
                } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomNavItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (isSelected) {
                        bottomNavItem.selectedIcon
                    } else {
                        bottomNavItem.unselectedIcon
                    }
                },
                label = { Text(bottomNavItem.label) },
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: NavigationDestinations,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem("Home", NavigationDestinations.Home, Icons.Outlined.Home, Icons.Default.Home),
    BottomNavItem("Vault", NavigationDestinations.Vault, Icons.Outlined.Wallet, Icons.Default.Wallet),
    BottomNavItem("Profile", NavigationDestinations.Profile, Icons.Outlined.Person, Icons.Default.Person)
)


