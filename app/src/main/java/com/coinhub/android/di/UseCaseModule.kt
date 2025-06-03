package com.coinhub.android.di

import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PaymentRepositoryImpl
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import com.coinhub.android.data.repositories.SourceRepositoryImpl
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
import com.coinhub.android.domain.use_cases.CreateSourceUseCase
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateSourceIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
    fun provideSourceIdUseCase() = ValidateSourceIdUseCase()

    @Provides
    @Singleton
    fun provideSignInWithCredentialUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        preferenceDataStoreImpl: PreferenceDataStoreImpl,
    ) =
        SignInWithCredentialUseCase(
            authRepositoryImpl = authRepositoryImpl,
            preferenceDataStoreImpl = preferenceDataStoreImpl
        )

    @Provides
    @Singleton
    fun provideSignUpWithCredentialUseCase(
        authRepositoryImpl: AuthRepositoryImpl, preferenceDataStoreImpl: PreferenceDataStoreImpl,
    ) = SignUpWithCredentialUseCase(
        authRepositoryImpl = authRepositoryImpl,
        preferenceDataStoreImpl = preferenceDataStoreImpl
    )

    @Provides
    @Singleton
    fun provideHandleResultOnSignInWithGoogleUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        preferenceDataStoreImpl: PreferenceDataStoreImpl,
    ) =
        SignInWithGoogleUseCase(
            authRepositoryImpl = authRepositoryImpl,
            preferenceDataStoreImpl = preferenceDataStoreImpl
        )

    @Provides
    @Singleton
    fun provideCheckUserSignedInUseCase(
        authRepositoryImpl: AuthRepositoryImpl,
        preferenceDataStoreImpl: PreferenceDataStoreImpl,
    ) = CheckUserSignedInUseCase(
        authRepositoryImpl = authRepositoryImpl,
        preferenceDataStoreImpl = preferenceDataStoreImpl
    )

    @Provides
    @Singleton
    fun provideCreateTopUpUseCase(
        paymentRepositoryImpl: PaymentRepositoryImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateTopUpUseCase(
        paymentRepositoryImpl = paymentRepositoryImpl,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideGetTopUpUseCase(
        paymentRepositoryImpl: PaymentRepositoryImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = GetTopUpUseCase(
        paymentRepositoryImpl = paymentRepositoryImpl,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideGetUserSourcesUseCase(
        userRepositoryImpl: UserRepositoryImpl,
        authRepositoryImpl: AuthRepositoryImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = GetUserSourcesUseCase(
        userRepositoryImpl = userRepositoryImpl,
        authRepositoryImpl = authRepositoryImpl,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCreateSourceUseCase(
        sourceRepositoryImpl: SourceRepositoryImpl,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateSourceUseCase(
        sourceRepositoryImpl = sourceRepositoryImpl,
        ioDispatcher = ioDispatcher
    )
}