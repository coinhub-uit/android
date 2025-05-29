package com.coinhub.android.presentation.navigation.app.nested

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.presentation.top_up_result.TopUpResultScreen

fun NavGraphBuilder.topUpNestedGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TopUpGraph>(startDestination = AppNavDestinations.TopUp) {
        composable<AppNavDestinations.TopUp> {
            TopUpScreen()
        }
        composable<AppNavDestinations.TopUpResult> {
            // TODO: Does it need args here?
            TopUpResultScreen(onNavigateToMain = {
                navController.navigate(AppNavDestinations.Main) {
                    popUpTo(AppNavDestinations.TopUp) {
                        inclusive = true
                    }
                }
            })
        }
    }
}
