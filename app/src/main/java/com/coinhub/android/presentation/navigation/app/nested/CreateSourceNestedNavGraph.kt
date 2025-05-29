package com.coinhub.android.presentation.navigation.app.nested

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.coinhub.android.presentation.create_source.CreateSourceScreen
import com.coinhub.android.presentation.navigation.AppNavDestinations

fun NavGraphBuilder.createSourceNestedGraph() {
    navigation<AppNavDestinations.CreateSourceGraph>(startDestination = AppNavDestinations.CreateSource) {
        composable<AppNavDestinations.CreateSource> {
            CreateSourceScreen()
        }
    }
}