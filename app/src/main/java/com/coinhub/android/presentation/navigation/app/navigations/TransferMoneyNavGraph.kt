package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.transfer_money.TransferMoneyScreen

fun NavGraphBuilder.transferMoneyGraph(navController : NavHostController) {
    navigation<AppNavDestinations.TransferMoneyGraph>(startDestination = AppNavDestinations.TransferMoney) {
        composable<AppNavDestinations.TransferMoney> {
            TransferMoneyScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable<AppNavDestinations.TransferMoneyResult> {
            // TransferMoneyResultScreen() TODO: IF have time
        }
    }
}
