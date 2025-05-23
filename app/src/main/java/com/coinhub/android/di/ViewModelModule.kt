package com.coinhub.android.di

import androidx.navigation.NavHostController
import com.coinhub.android.BuildConfig
import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.presentation.viewmodels.SignInViewModel
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
object ViewModelModule {
    @Singleton
    @Provides
    fun provideSignInViewModel(
        supabaseClient: SupabaseClient,
        userApiService: UserApiService,
    ): SignInViewModel {
        return SignInViewModel(
            supabaseClient = supabaseClient,
            userApiService = userApiService,
        )
    }
}
