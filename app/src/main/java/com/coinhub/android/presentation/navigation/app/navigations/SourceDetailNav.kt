package com.coinhub.android.presentation.navigation.app.navigations

import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.navTypeOf
import com.coinhub.android.presentation.source_detail.SourceDetailScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.sourceDetailNav(navController: NavHostController) {
    composable<AppNavDestinations.SourceDetail>(
        typeMap = mapOf(typeOf<SourceModel>() to navTypeOf<SourceModel>())
    ) { navBackStackEntry ->
        val sourceDetail = navBackStackEntry.toRoute<AppNavDestinations.SourceDetail>()
        CompositionLocalProvider(LocalAnimatedVisibilityScope provides this@composable) {
            SourceDetailScreen(
                source = sourceDetail.source, onBack = { navController.navigateUp() })
        }
    }
}
