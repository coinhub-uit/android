package com.coinhub.android.di

import com.coinhub.android.domain.managers.UserManager
import com.coinhub.android.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonManagerModule {
    @Provides
    @Singleton
    fun provideUserManager(supabaseClient: SupabaseClient, userRepository: UserRepository) =
        UserManager(supabaseClient = supabaseClient, userRepository = userRepository)
}
