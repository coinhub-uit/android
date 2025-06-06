package com.coinhub.android.di

import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PaymentRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.SourceRepository
import com.coinhub.android.domain.repositories.TicketRepository
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CheckUserRegisterProfileUseCase
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
import com.coinhub.android.domain.use_cases.CreateSourceUseCase
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.domain.use_cases.GetUserSourcesUseCase
import com.coinhub.android.domain.use_cases.GetUserTicketsUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateAmountCreateTicketUseCase
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

// TODO: Maybe scoped to view model
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
    fun provideValidateAmountCreateTicketUseCase() = ValidateAmountCreateTicketUseCase()

    @Provides
    @Singleton
    fun provideValidateSourceIdUseCase() = ValidateSourceIdUseCase()

    @Provides
    @Singleton
    fun provideSignInWithCredentialUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) =
        SignInWithCredentialUseCase(
            authRepository = authRepository,
            preferenceDataStore = preferenceDataStore
        )

    @Provides
    @Singleton
    fun provideSignUpWithCredentialUseCase(
        authRepository: AuthRepository, preferenceDataStore: PreferenceDataStore,
    ) = SignUpWithCredentialUseCase(
        authRepository = authRepository,
        preferenceDataStore = preferenceDataStore
    )

    @Provides
    @Singleton
    fun provideHandleResultOnSignInWithGoogleUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) =
        SignInWithGoogleUseCase(
            authRepository = authRepository,
            preferenceDataStore = preferenceDataStore
        )

    @Provides
    @Singleton
    fun provideCheckUserSignedInUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) = CheckUserSignedInUseCase(
        authRepository = authRepository,
        preferenceDataStore = preferenceDataStore
    )

    @Provides
    @Singleton
    fun provideCreateTopUpUseCase(
        paymentRepository: PaymentRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateTopUpUseCase(
        paymentRepository = paymentRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideGetTopUpUseCase(
        paymentRepository: PaymentRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = GetTopUpUseCase(
        paymentRepository = paymentRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideGetUserSourcesUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = GetUserSourcesUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCreateSourceUseCase(
        sourceRepository: SourceRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateSourceUseCase(
        sourceRepository = sourceRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCheckUserRegisterProfileUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CheckUserRegisterProfileUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideGetUserTicketsUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = GetUserTicketsUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCreateTicketUseCase(
        ticketRepository: TicketRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = com.coinhub.android.domain.use_cases.CreateTicketUseCase(
        ticketRepository = ticketRepository,
        ioDispatcher = ioDispatcher
    )
}