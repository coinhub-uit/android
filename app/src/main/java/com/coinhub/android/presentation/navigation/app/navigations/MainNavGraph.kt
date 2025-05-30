package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.home.HomeScreen
import com.coinhub.android.presentation.menu.MenuScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.transfer_money.TransferMoneyScreen
import com.coinhub.android.presentation.vault.VaultScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    navigation<AppNavDestinations.MainGraph>(startDestination = AppNavDestinations.Home) {
        composable<AppNavDestinations.Home>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            HomeScreen(
                navigateToCreateSource = { navController.navigate(AppNavDestinations.CreateSourceGraph) },
                // TODO: May contains args here
                navigateToSourceDetail = { navController.navigate(AppNavDestinations.SourceDetail) },
                navigateToTopUp = { navController.navigate(AppNavDestinations.TopUpGraph) },
                navigateToNotification = { navController.navigate(AppNavDestinations.Notification) },
                navigateToAiChat = { navController.navigate(AppNavDestinations.AiChat) },
                navigateToTransferMoney = { navController.navigate(AppNavDestinations.TransferMoneyGraph) })
        }
        composable<AppNavDestinations.Vault>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            VaultScreen(
                navigateToCreateTicket = { navController.navigate(AppNavDestinations.CreateTicketGraph) },
                // TODO: May contains args here
                navigateToTicketDetail = { navController.navigate((AppNavDestinations.TicketDetail)) })
        }
        composable<AppNavDestinations.Menu>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            MenuScreen(
                navigateToEditProfile = { navController.navigate(AppNavDestinations.EditProfile) },
                navigateToSettings = { navController.navigate(AppNavDestinations.Settings) })
        }
    }
}
