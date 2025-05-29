package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.presentation.top_up_result.TopUpResultScreen

fun NavGraphBuilder.topUpGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TopUpGraph>(startDestination = AppNavDestinations.TopUp) {
        composable<AppNavDestinations.TopUp> {
            TopUpScreen(
                navigateToTopUpResult = { navController.navigate(AppNavDestinations.TopUpResult) }
            )
        }
        composable<AppNavDestinations.TopUpResult> {
            // TODO: Does it need args here?
            TopUpResultScreen(navigateToMain = {
                navController.navigate(AppNavDestinations.MainGraph) {
                    popUpTo(AppNavDestinations.TopUp) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
