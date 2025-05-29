package com.coinhub.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.top_up_result.TopUpResultScreen

@Composable
fun TopUpGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationDestinations.TopUp) {
        composable<NavigationDestinations.TopUp> {
            TopUpScreen()
        }
        composable<NavigationDestinations.TopUpResult> {
            // TODO: Does it need args here?
            TopUpResultScreen(onNavigateToMain = {
                navController.navigate(NavigationDestinations.MainGraph) {
                    popUpTo(NavigationDestinations.AuthGraph) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
