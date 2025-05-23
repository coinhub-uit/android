package com.coinhub.android.di

import com.coinhub.android.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseClientModule {
    private const val SUPABASE_URL = BuildConfig.supabaseUrl
    private const val SUPABASE_KEY = BuildConfig.supabaseAnonKey

    @Singleton
    @Provides
    fun provideSupabaseClient(): SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        )
        {
            install(Auth);
            install(ComposeAuth) {
                googleNativeLogin(serverClientId = BuildConfig.oAuthGoogleAndroidClient)
            }
        }
        return client
    }
}
