package com.coinhub.android.di

import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
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
    fun provideSignInWithCredentialUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
    ) =
        SignInWithCredentialUseCase(
            authRepositoryImpl = authRepositoryImpl,
            sharedPreferenceRepositoryImpl = sharedPreferenceRepositoryImpl
        )

    @Provides
    @Singleton
    fun provideSignUpWithCredentialUseCase(
        authRepositoryImpl: AuthRepositoryImpl, sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
    ) = SignUpWithCredentialUseCase(
        authRepositoryImpl = authRepositoryImpl,
        sharedPreferenceRepositoryImpl = sharedPreferenceRepositoryImpl
    )

    @Provides
    @Singleton
    fun provideHandleResultOnSignInWithGoogleUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
    ) =
        HandleResultOnSignInWithGoogleUseCase(
            authRepositoryImpl = authRepositoryImpl,
            sharedPreferenceRepositoryImpl = sharedPreferenceRepositoryImpl
        )

    @Provides
    @Singleton
    fun provideCheckUserSignedInUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl,
    ) = CheckUserSignedInUseCase(
        authRepositoryImpl = authRepositoryImpl,
        sharedPreferenceRepositoryImpl = sharedPreferenceRepositoryImpl
    )

    @Provides
    @Singleton
    fun provideRegisterProfileUseCase(authRepositoryImpl: AuthRepositoryImpl) = RegisterProfileUseCase(
        authRepositoryImpl =
            authRepositoryImpl
    )
}