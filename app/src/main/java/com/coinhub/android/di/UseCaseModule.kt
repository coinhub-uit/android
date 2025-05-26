package com.coinhub.android.di

import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.domain.use_cases.HandleResultOnSignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.RegisterProfileUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideValidateEmailUseCase() = ValidateEmailUseCase()

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()

    @Provides
    @Singleton
    fun provideValidateConfirmPasswordUseCase() = ValidateConfirmPasswordUseCase()

    @Provides
    @Singleton
    fun provideSignInWithCredentialUseCase(authRepositoryImpl: AuthRepositoryImpl) =
        SignInWithCredentialUseCase(authRepositoryImpl = authRepositoryImpl)

    @Provides
    @Singleton
    fun provideSignUpWithCredentialUseCase(authRepositoryImpl: AuthRepositoryImpl) = SignUpWithCredentialUseCase(
        authRepositoryImpl = authRepositoryImpl
    )

    @Provides
    @Singleton
    fun provideHandleResultOnSignInWithGoogleUseCase(authRepositoryImpl: AuthRepositoryImpl) =
        HandleResultOnSignInWithGoogleUseCase(
            authRepositoryImpl = authRepositoryImpl
        )

    @Provides
    @Singleton
    fun provideRegisterProfileUseCase(authRepositoryImpl: AuthRepositoryImpl) = RegisterProfileUseCase(
        authRepositoryImpl =
            authRepositoryImpl
    )
}