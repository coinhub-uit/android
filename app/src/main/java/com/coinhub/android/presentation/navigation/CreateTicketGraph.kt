package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.create_ticket.CreateTicketScreen

@Composable
fun CreateTicketGraph(
    navController: NavHostController
) {
    NavHost(navController=navController, startDestination = NavigationDestinations.CreateTicket) {
            composable<NavigationDestinations.CreateTicket> {
                CreateTicketScreen()
            }
//            composable<NavigationDestinations.ConfirmCreateTicket> {
//                 ConfirmCreateTicketScreen()
//            }
        }
}
