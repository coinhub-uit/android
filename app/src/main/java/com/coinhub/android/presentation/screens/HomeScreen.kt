package com.coinhub.android.presentation.screens

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.coinhub.android.presentation.viewmodels.AuthViewModel
import com.coinhub.android.authentication.SupabaseViewModel
import com.coinhub.android.data.models.User

@Composable
fun HomeScreen(
    navController: NavController,
    supabaseViewModel: SupabaseViewModel,
    apiServerViewModel: AuthViewModel,
) {
    var user: User?
    LaunchedEffect(Unit) {
        val userId: String? = supabaseViewModel.userId.value
        if (userId.isNullOrEmpty()) {
            return@LaunchedEffect
        }
        apiServerViewModel.getUserById(userId)
        user = apiServerViewModel.user.value
    }

    Text("This is HomeScreen")
    Button(onClick = {
        supabaseViewModel.signOut(
            navController = navController,
            context = context
        )
    }) {
        Text("Sign Out")
    }
}


