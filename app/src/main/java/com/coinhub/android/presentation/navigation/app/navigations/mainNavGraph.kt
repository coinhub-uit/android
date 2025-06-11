package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.home.HomeScreen
import com.coinhub.android.presentation.menu.MenuScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.ticket.TicketScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    startDestination: AppNavDestinations?,
) {
    navigation<AppNavDestinations.MainGraph>(startDestination = startDestination ?: AppNavDestinations.Home) {
        composable<AppNavDestinations.Home>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                HomeScreen(
                    onCreateSource = { navController.navigate(AppNavDestinations.CreateSourceGraph) },
                    onToSourceDetail = { source: SourceModel ->
                        navController.navigate(
                            AppNavDestinations.SourceDetail(
                                source = source,
                            )
                        )
                    },
                    onTopUp = { navController.navigate(AppNavDestinations.TopUpGraph) },
                    onNotification = { navController.navigate(AppNavDestinations.Notification) },
                    onAiChat = { navController.navigate(AppNavDestinations.AiChat) },
                    onTransferMoney = { navController.navigate(AppNavDestinations.TransferMoneyGraph) },
                    onTransferMoneyQr = {
                        navController.navigate(AppNavDestinations.TransferMoneyQrGraph)
                    },
                )
            }
        }
        composable<AppNavDestinations.Tickets>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                TicketScreen(
                    onCreateTicket = { navController.navigate(AppNavDestinations.CreateTicketGraph) },
                    // TODO: May contains args here
                    onTicketDetail = { ticketId ->
                        navController.navigate(AppNavDestinations.TicketDetail(ticketId = ticketId))
                    })
            }
        }
        composable<AppNavDestinations.Menu>(
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
        ) {
            MenuScreen(
                onEditProfile = { navController.navigate(AppNavDestinations.EditProfile) },
                onSettings = { navController.navigate(AppNavDestinations.Settings) },
                onCredentialChange = { navController.navigate(AppNavDestinations.CredentialChange) },
            )
        }
    }
}
