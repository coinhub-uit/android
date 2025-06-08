package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_ticket.CreateTicketScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope

fun NavGraphBuilder.createTicketGraph(navController: NavHostController) {
    navigation<AppNavDestinations.CreateTicketGraph>(startDestination = AppNavDestinations.CreateTicket) {
        composable<AppNavDestinations.CreateTicket> {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                CreateTicketScreen(
                    onBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
        composable<AppNavDestinations.ConfirmCreateTicket> {
            // ConfirmCreateTicketScreen() TODO: IF have time
        }
    }
}
