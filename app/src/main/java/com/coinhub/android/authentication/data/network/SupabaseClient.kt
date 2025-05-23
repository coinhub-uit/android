package com.coinhub.android.authentication.data.network

import com.coinhub.android.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.supabaseUrl,
        supabaseKey = BuildConfig.supabaseAnonKey
    )
    {
        install(Auth);
        install(ComposeAuth) {
            googleNativeLogin(serverClientId = BuildConfig.oAuthGoogleAndroidClient)
        }
    }
}
