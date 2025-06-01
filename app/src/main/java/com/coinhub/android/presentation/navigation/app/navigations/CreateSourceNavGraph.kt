package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_source.CreateSourceScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.createSourceGraph(navController: NavHostController) {
    navigation<AppNavDestinations.CreateSourceGraph>(startDestination = AppNavDestinations.CreateSource) {
        composable<AppNavDestinations.CreateSource> {
            CreateSourceScreen(
                onBack = { navController.navigateUp() }
            )
        }
    }
}