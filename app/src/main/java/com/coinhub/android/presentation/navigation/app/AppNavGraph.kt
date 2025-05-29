package com.coinhub.android.presentation.navigation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.common.permission_requests.RequestNotificationPermissionDialog
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.components.AppBottomBar
import com.coinhub.android.presentation.navigation.app.components.AppTopBar
import com.coinhub.android.presentation.navigation.app.components.bottomNavItems
import com.coinhub.android.presentation.navigation.app.navigations.aiChatNav
import com.coinhub.android.presentation.navigation.app.navigations.createSourceGraph
import com.coinhub.android.presentation.navigation.app.navigations.createTicketGraph
import com.coinhub.android.presentation.navigation.app.navigations.editProfileNav
import com.coinhub.android.presentation.navigation.app.navigations.mainNavGraph
import com.coinhub.android.presentation.navigation.app.navigations.notificationNav
import com.coinhub.android.presentation.navigation.app.navigations.settingNav
import com.coinhub.android.presentation.navigation.app.navigations.sourceDetailNav
import com.coinhub.android.presentation.navigation.app.navigations.ticketDetailNav
import com.coinhub.android.presentation.navigation.app.navigations.topUpGraph

@Composable
fun AppNavGraph() {
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

    Scaffold(topBar = {
        if (isInMainGraph) AppTopBar(navController = navController)
    }, bottomBar = {
        AnimatedVisibility(
            visible = isInMainGraph,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            AppBottomBar(navController = navController)
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = AppNavDestinations.MainGraph) {
                mainNavGraph(navController = navController)
                createSourceGraph()
                topUpGraph(navController = navController)
                sourceDetailNav()
                createTicketGraph()
                ticketDetailNav()
                notificationNav()
                aiChatNav()
                editProfileNav(navController = navController)
                settingNav()
            }
        }
    }
}