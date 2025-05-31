package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.presentation.home.HomeScreen
import com.coinhub.android.presentation.menu.MenuScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.vault.VaultScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController, supabaseService: SupabaseService) {
    navigation<AppNavDestinations.MainGraph>(startDestination = AppNavDestinations.Home) {
        composable<AppNavDestinations.Home>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            HomeScreen(
                onCreateSource = { navController.navigate(AppNavDestinations.CreateSourceGraph) },
                onToSourceDetail = { navController.navigate(AppNavDestinations.SourceDetail) },
                onTopUp = { navController.navigate(AppNavDestinations.TopUpGraph) },
                onNotification = { navController.navigate(AppNavDestinations.Notification) },
                onAiChat = { navController.navigate(AppNavDestinations.AiChat) },
                onTransferMoney = { navController.navigate(AppNavDestinations.TransferMoneyGraph) })
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
                onEditProfile = { navController.navigate(AppNavDestinations.EditProfile) },
                onSettings = { navController.navigate(AppNavDestinations.Settings) },
                onCredentialChange = { navController.navigate(AppNavDestinations.CredentialChange) },
                onSignOut = { supabaseService.signOut() }
            )
        }
    }
}
