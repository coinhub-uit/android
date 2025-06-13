package com.coinhub.android.di

import com.coinhub.android.BuildConfig
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import com.coinhub.android.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    private const val SUPABASE_URL = BuildConfig.supabaseUrl
    private const val SUPABASE_KEY = BuildConfig.supabaseAnonKey

    @Singleton
    @Provides
    fun provideSupabaseClient(): SupabaseClient {
        val client = createSupabaseClient(
            supabaseUrl = SUPABASE_URL, supabaseKey = SUPABASE_KEY
        ) {
            install(Auth) {
                alwaysAutoRefresh = true
            }
            install(ComposeAuth) {
                googleNativeLogin(serverClientId = BuildConfig.oAuthGoogleAndroidClient)
            }
        }
        return client
    }

    @Singleton
    @Provides
    fun provideSupabaseService(
        supabaseClient: SupabaseClient,
        sharedPreferenceRepositoryImpl: PreferenceDataStoreImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userRepository: Provider<UserRepository>,
    ) = SupabaseService(supabaseClient, sharedPreferenceRepositoryImpl, userRepository, ioDispatcher)
}
