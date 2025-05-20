package com.coinhub.android.authentication.presentation.home

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.coinhub.android.authentication.SupabaseViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    supabaseViewModel: SupabaseViewModel,
    context: Context
) {
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


