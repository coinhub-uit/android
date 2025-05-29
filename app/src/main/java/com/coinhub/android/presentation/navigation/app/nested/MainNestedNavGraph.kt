package com.coinhub.android.presentation.navigation.app.nested

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.home.HomeScreen
import com.coinhub.android.presentation.menu.MenuScreen
import com.coinhub.android.presentation.vault.VaultScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.mainNestedNavGraph(navController: NavHostController) {
    navigation<AppNavDestinations.MainGraph>(startDestination = AppNavDestinations.Home) {
        composable<AppNavDestinations.Home> {
            HomeScreen(
                navigateToCreateSource = { navController.navigate(AppNavDestinations.CreateSourceGraph) },
                // TODO: May contains args here
                navigateToSourceDetail = { navController.navigate(AppNavDestinations.SourceDetail) },
                navigateToTopUp = { navController.navigate(AppNavDestinations.TopUpGraph) },
            )
        }
        composable<AppNavDestinations.Vault> {
            VaultScreen(
                navigateToCreateTicket = { navController.navigate(AppNavDestinations.CreateTicketGraph) },
                // TODO: May contains args here
                navigateToTicketDetail = { navController.navigate((AppNavDestinations.TicketDetail)) }
            )
        }
        composable<AppNavDestinations.Menu> {
            MenuScreen(
                navigateToEditProfile = { navController.navigate(AppNavDestinations.EditProfile) },
                navigateToSettings = { navController.navigate(AppNavDestinations.Settings) }
            )
        }
    }
}
