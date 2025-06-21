package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.transfer_money.TransferMoneyScreen
import com.coinhub.android.presentation.transfer_money_scan.TransferMoneyScanScreen

fun NavGraphBuilder.transferMoneyQrNav(navController: NavHostController) {
    navigation<AppNavDestinations.TransferMoneyQrGraph>(startDestination = AppNavDestinations.TransferMoneyQrScan) {
        composable<AppNavDestinations.TransferMoneyQrScan> {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                TransferMoneyScanScreen(
                    onScanned = { sourceId: String ->
                        navController.navigate(
                            AppNavDestinations.TransferMoneyQr(
                                sourceId = sourceId
                            ),
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
        composable<AppNavDestinations.TransferMoneyQr> { navBackStackEntry ->
            val transferMoneyQr = navBackStackEntry.toRoute<AppNavDestinations.TransferMoneyQr>()
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                TransferMoneyScreen(
                    sourceId = transferMoneyQr.sourceId,
                    onBack = {
                        navController.navigate(
                            AppNavDestinations.MainGraph,
                        )
                    }
                )
            }
        }
    }
}
