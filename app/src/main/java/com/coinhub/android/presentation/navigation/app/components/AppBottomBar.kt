package com.coinhub.android.presentation.navigation.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.coinhub.android.presentation.navigation.AppNavDestinations

@Composable
fun AppBottomBar(navController: NavHostController) {
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
                    Icon(
                        imageVector = if (isSelected) {
                            bottomNavItem.selectedIcon
                        } else {
                            bottomNavItem.unselectedIcon
                        },
                        contentDescription = bottomNavItem.label
                    )
                },
                label = { Text(bottomNavItem.label) },
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: AppNavDestinations,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem("Home", AppNavDestinations.Home, Icons.Outlined.Home, Icons.Default.Home),
    BottomNavItem("Tickets", AppNavDestinations.Tickets, Icons.Outlined.Sell, Icons.Default.Sell),
    BottomNavItem("Menu", AppNavDestinations.Menu, Icons.Outlined.Menu, Icons.Default.Menu)
)