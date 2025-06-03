package com.coinhub.android.presentation.navigation.app.navigations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.source_detail.SourceDetailScreen

fun NavGraphBuilder.sourceDetailNav(navController: NavHostController) {
    composable<AppNavDestinations.SourceDetail> { navBackStackEntry ->
        val sourceDetail = navBackStackEntry.toRoute<AppNavDestinations.SourceDetail>()
        SourceDetailScreen(
            sourceId = sourceDetail.sourceId, onBack = { navController.navigateUp() })
    }
}
