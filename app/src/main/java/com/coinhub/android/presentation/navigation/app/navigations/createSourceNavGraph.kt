package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_source.CreateSourceScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope

fun NavGraphBuilder.createSourceNavGraph(navController: NavHostController) {
    navigation<AppNavDestinations.CreateSourceGraph>(startDestination = AppNavDestinations.CreateSource) {
        composable<AppNavDestinations.CreateSource> {
            CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
                CreateSourceScreen(
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}