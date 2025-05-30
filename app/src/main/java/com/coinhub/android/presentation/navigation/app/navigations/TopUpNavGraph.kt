package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.coinhub.android.BuildConfig
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.presentation.top_up_result.TopUpResultScreen

fun NavGraphBuilder.topUpGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TopUpGraph>(startDestination = AppNavDestinations.TopUp) {
        composable<AppNavDestinations.TopUp> {
            TopUpScreen(onTopUpResult = { topUp: AppNavDestinations.TopUpResult ->
                navController.navigate(AppNavDestinations.TopUpResult(vnpResponseCode = topUp.vnpResponseCode))
            }, onBack = { navController.navigateUp() })
        }
        composable<AppNavDestinations.TopUpResult>(
            deepLinks = listOf(
                navDeepLink<AppNavDestinations.TopUpResult>(
                    basePath = BuildConfig.vnpayReturnUrl
                )
            )
        ) { backStackEntry ->
            val topUp = backStackEntry.toRoute<AppNavDestinations.TopUpResult>()
            TopUpResultScreen(
                onMain = {
                    navController.navigate(AppNavDestinations.MainGraph) {
                        popUpTo(AppNavDestinations.TopUpGraph) {
                            inclusive = true
                        }
                    }
                }, topUp = topUp
            )
        }
    }
}
