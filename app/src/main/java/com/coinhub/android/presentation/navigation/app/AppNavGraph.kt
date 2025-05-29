package com.coinhub.android.presentation.navigation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.ai_chat.AiChatScreen
import com.coinhub.android.presentation.common.permission_requests.RequestNotificationPermissionDialog
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.components.BottomBar
import com.coinhub.android.presentation.navigation.app.components.bottomNavItems
import com.coinhub.android.presentation.navigation.app.nested.createSourceNestedGraph
import com.coinhub.android.presentation.navigation.app.nested.createTicketNestedGraph
import com.coinhub.android.presentation.navigation.app.nested.mainNestedNavGraph
import com.coinhub.android.presentation.navigation.app.nested.topUpNestedGraph
import com.coinhub.android.presentation.notification.NotificationScreen
import com.coinhub.android.presentation.profile.ProfileScreen
import com.coinhub.android.presentation.settings.SettingsScreen
import com.coinhub.android.presentation.source_detail.SourceDetailScreen
import com.coinhub.android.presentation.ticket_detail.TicketDetailScreen

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


    Scaffold(bottomBar = {
        if (isInMainGraph)
            BottomBar(navController)
    }, floatingActionButton = {
        if (isInMainGraph)
            FloatingActionButton(
                onClick = { navController.navigate(AppNavDestinations.AiChat) }) {
                Icon(Icons.AutoMirrored.Default.Message, "AI Chat")
            }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = AppNavDestinations.MainGraph) {
                mainNestedNavGraph(navController = navController)

                createSourceNestedGraph()

                topUpNestedGraph(navController = navController)

                composable<AppNavDestinations.SourceDetail> {
                    SourceDetailScreen()
                }

                createTicketNestedGraph()

                composable<AppNavDestinations.TicketDetail> {
                    TicketDetailScreen()
                }

                composable<AppNavDestinations.Notification> {
                    NotificationScreen()
                }

                composable<AppNavDestinations.AiChat> {
                    AiChatScreen()
                }

                composable<AppNavDestinations.EditProfile> {
                    ProfileScreen(
                        onSuccess = { navController.navigateUp() })
                }

                composable<AppNavDestinations.Settings> {
                    SettingsScreen()
                }
            }
        }
    }
}