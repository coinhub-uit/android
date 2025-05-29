package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.ticket_detail.TicketDetailScreen

fun NavGraphBuilder.ticketDetailNav() {
    composable<AppNavDestinations.Notification>{
        TicketDetailScreen()
    }
}
