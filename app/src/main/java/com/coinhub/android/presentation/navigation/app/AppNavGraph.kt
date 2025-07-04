package com.coinhub.android.presentation.navigation.app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.common.permission_requests.RequestNotificationPermissionDialog
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.components.AppBottomBar
import com.coinhub.android.presentation.navigation.app.components.bottomNavItems
import com.coinhub.android.presentation.navigation.app.navigations.aiChatNav
import com.coinhub.android.presentation.navigation.app.navigations.createSourceNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.createTicketNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.mainNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.menuNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.notificationNav
import com.coinhub.android.presentation.navigation.app.navigations.sourceDetailNav
import com.coinhub.android.presentation.navigation.app.navigations.ticketDetailNav
import com.coinhub.android.presentation.navigation.app.navigations.topUpNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.transferMoneyNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.transferMoneyQrNav
import kotlinx.coroutines.flow.SharedFlow

// The inner padding of scaffold isn't needed.. grammar
@OptIn(ExperimentalSharedTransitionApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavGraph(
    destinationFlow: SharedFlow<String>,
    intentFlow: SharedFlow<Intent>,
) {
    RequestNotificationPermissionDialog()

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val mainGraphDestinations = bottomNavItems.map { it.route }

    val isInMainGraph = currentDestination?.hierarchy?.any { navDestination ->
        mainGraphDestinations.any {
            navDestination.hasRoute(it::class)
        }
    } == true

    LaunchedEffect(Unit) {
        destinationFlow.collect {
            val appDestination = when (it) {
                AppNavDestinations.Tickets.toString() -> AppNavDestinations.Tickets
                AppNavDestinations.TransferMoneyQrGraph.toString() -> AppNavDestinations.TransferMoneyQrGraph
                else -> null
            }
            if (appDestination != null) {
                navController.navigate(appDestination) {
                    popUpTo(AppNavDestinations.MainGraph) {
                        inclusive = true
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        intentFlow.collect { intent ->
            navController.handleDeepLink(intent)
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isInMainGraph,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                AppBottomBar(navController = navController)
            }
        },
    ) {
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedTransitionScope provides this@SharedTransitionLayout) {
                // NOTE: I know this is complicated and it can be fixed by making AppNavDestinations inherit strictly
                NavHost(
                    navController = navController, startDestination = AppNavDestinations.MainGraph
                ) {
                    mainNavGraph(navController = navController)
                    transferMoneyQrNav(navController = navController)
                    createSourceNavGraph(navController = navController)
                    topUpNavGraph(navController = navController)
                    sourceDetailNav(navController = navController)
                    transferMoneyNavGraph(navController = navController)
                    createTicketNavGraph(navController = navController)
                    ticketDetailNav(navController = navController)
                    notificationNav(navController = navController)
                    aiChatNav(navController = navController)
                    menuNavGraph(navController = navController)
                }
            }
        }
    }
}