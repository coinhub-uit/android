package com.coinhub.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.screens.AuthScreen
import com.coinhub.android.presentation.screens.HomeScreen

@Composable
fun Navigation() {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (true) Home else Auth
    )
    {
        composable<Sigin> {
            AuthScreen(
                navController = navController,
            )
        }
        composable<Home> {
            HomeScreen(
                navController = navController,
                supabaseViewModel = supabaseViewModel,
                context = context,
                apiServerViewModel = apiServerViewModel
            )
        }
    }
}

