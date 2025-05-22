package com.coinhub.android.authentication.presentation.home

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.coinhub.android.authentication.ApiServerViewModel
import com.coinhub.android.authentication.SupabaseViewModel
import com.coinhub.android.authentication.data.model.User

@Composable
fun HomeScreen(
    navController: NavController,
    supabaseViewModel: SupabaseViewModel,
    context: Context,
    apiServerViewModel: ApiServerViewModel,
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


