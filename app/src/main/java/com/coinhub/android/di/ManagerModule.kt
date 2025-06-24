package com.coinhub.android.di

import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.managers.LockHashingManager
import com.coinhub.android.domain.managers.ThemeManger
import com.coinhub.android.domain.repositories.PreferenceDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonManagerModule {

    @Provides
    @Singleton
    fun provideThemeManager(preferenceDataStore: PreferenceDataStore, @IoDispatcher ioDispatcher: CoroutineDispatcher) =
        ThemeManger(preferenceDataStore, ioDispatcher = ioDispatcher)

    @Provides
    fun provideLockHashingManager(preferenceDataStore: PreferenceDataStore) = LockHashingManager(preferenceDataStore)

    @Singleton
    @Provides
    fun provideUserRepository(userApiService: UserApiService, preferenceDataStore: PreferenceDataStore) =
        UserRepositoryImpl(
            userApiService = userApiService,
            preferenceDataStore = preferenceDataStore
        )
}
