package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_ticket.CreateTicketScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.createTicketGraph(navController: NavHostController) {
    navigation<AppNavDestinations.CreateTicketGraph>(startDestination = AppNavDestinations.CreateTicket) {
        composable<AppNavDestinations.CreateTicket> {
            CreateTicketScreen(
                onBack = {
                    navController.navigateUp()
                },
                onMain = {
                    navController.navigate(AppNavDestinations.MainGraph) {
                        popUpTo(AppNavDestinations.CreateTicketGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AppNavDestinations.ConfirmCreateTicket> {
            // ConfirmCreateTicketScreen() TODO: IF have time
        }
    }
}
