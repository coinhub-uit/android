package com.coinhub.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coinhub.android.presentation.screens.AuthScreen
import com.coinhub.android.presentation.screens.HomeScreen
import com.coinhub.android.presentation.screens.sign_in.SignInScreen

@Composable
fun NavGraph() {
    val context = LocalContext.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (true) Home else Auth
    )
    {
        composable<SignIn> {
            SignInScreen(
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

