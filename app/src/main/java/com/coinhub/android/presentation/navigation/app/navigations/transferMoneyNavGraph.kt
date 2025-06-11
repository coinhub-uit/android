package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.transfer_money.TransferMoneyScreen

fun NavGraphBuilder.transferMoneyNavGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TransferMoneyGraph>(startDestination = AppNavDestinations.TransferMoney) {
        composable<AppNavDestinations.TransferMoney> {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                TransferMoneyScreen(
                    onBack = { navController.navigateUp() },
                )
            }
        }
        composable<AppNavDestinations.TransferMoneyResult> {
            // TransferMoneyResultScreen() TODO: IF have time
        }
    }
}
