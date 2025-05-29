package com.coinhub.android.presentation.navigation.app.nested

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_ticket.CreateTicketScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.createTicketNestedGraph() {
    navigation<AppNavDestinations.CreateTicketGraph>(startDestination = AppNavDestinations.CreateTicket) {
            composable<AppNavDestinations.CreateTicket> {
                CreateTicketScreen()
            }
            composable<AppNavDestinations.ConfirmCreateTicket> {
                 // ConfirmCreateTicketScreen() TODO: IF have time
            }
        }
}
