package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.coinhub.android.BuildConfig
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.presentation.top_up_result.TopUpResultScreen

fun NavGraphBuilder.topUpNavGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TopUpGraph>(startDestination = AppNavDestinations.TopUp) {
        composable<AppNavDestinations.TopUp> {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                TopUpScreen(onBack = { navController.navigateUp() })
            }
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
                topUpId = topUp.vnpTxnRef,
                onMain = {
                    navController.navigate(AppNavDestinations.MainGraph) {
                        popUpTo(AppNavDestinations.TopUpGraph) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
