package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.coinhub.android.data.dtos.CreateTopUpDto
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.top_up.TopUpScreen
import com.coinhub.android.presentation.top_up_result.TopUpResultScreen

fun NavGraphBuilder.topUpGraph(navController: NavHostController) {
    navigation<AppNavDestinations.TopUpGraph>(startDestination = AppNavDestinations.TopUp) {
        composable<AppNavDestinations.TopUp> {
            TopUpScreen(
                onTopUpResult = { createTopUpDto: CreateTopUpDto ->
                    navController.navigate(
                        AppNavDestinations.TopUpResult(
                            createTopUpDto
                        )
                    )
                },
                onBack = { navController.navigateUp() }
            )
        }
        composable<AppNavDestinations.TopUpResult> { backStackEntry ->
            val createTopUpDto = backStackEntry.toRoute<AppNavDestinations.TopUpResult>()
            TopUpResultScreen(
                onMain = {
                    navController.navigate(AppNavDestinations.MainGraph) {
                        popUpTo(AppNavDestinations.TopUpGraph) {
                            inclusive = true
                        }
                    }
                },
                createTopUpDto = createTopUpDto.createTopUpDto
            )
        }
    }
}
