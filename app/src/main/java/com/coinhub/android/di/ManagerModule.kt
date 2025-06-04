package com.coinhub.android.di

import com.coinhub.android.domain.managers.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonManagerModule {
    @Provides
    @Singleton
    fun provideUserManager() = UserManager()
}
