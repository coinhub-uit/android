package com.coinhub.android.di

import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.SharedPreferenceRepositoryImpl
import com.coinhub.android.data.repositories.TopUpRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(supabaseService: SupabaseService, userApiService: UserApiService) =
        AuthRepositoryImpl(supabaseService = supabaseService, userApiService = userApiService)

    @Singleton
    @Provides
    fun provideTopUpRepository() = TopUpRepositoryImpl()

    @Singleton
    @Provides
    fun provideSharedPreferencesRepository() = SharedPreferenceRepositoryImpl()
}