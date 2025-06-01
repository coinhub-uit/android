package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.ticket_detail.TicketDetailScreen

fun NavGraphBuilder.ticketDetailNav(navController: NavHostController) {
    composable<AppNavDestinations.TicketDetail>{ navBackStackEntry ->
        val ticketDetail = navBackStackEntry.toRoute<AppNavDestinations.TicketDetail>()
        TicketDetailScreen(
            ticketId = ticketDetail.ticketId,
            onBack = { navController.navigateUp() }
        )
    }
}
