package com.coinhub.android.di

import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PaymentRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CheckProfileAvailableUseCase
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
import com.coinhub.android.domain.use_cases.CreateSourceUseCase
import com.coinhub.android.domain.use_cases.CreateTicketUseCase
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.TransferMoneyUseCase
import com.coinhub.android.domain.use_cases.ValidateAmountCreateTicketUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateSourceIdUseCase
import com.coinhub.android.domain.use_cases.WithdrawTicketUseCase
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
    fun provideValidateEmailUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateEmailUseCase(defaultDispatcher)

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidatePasswordUseCase(defaultDispatcher)

    @Provides
    @Singleton
    fun provideValidateConfirmPasswordUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateConfirmPasswordUseCase(defaultDispatcher)

    @Provides
    @Singleton
    fun provideValidateAmountCreateTicketUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateAmountCreateTicketUseCase(defaultDispatcher)

    @Provides
    @Singleton
    fun provideValidateSourceIdUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateSourceIdUseCase(defaultDispatcher)

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
    ) = GetTopUpUseCase(
        paymentRepository = paymentRepository,
    )

    @Provides
    @Singleton
    fun provideCreateSourceUseCase(
        sourceApiService: SourceApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateSourceUseCase(
        sourceApiService = sourceApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCheckUserRegisterProfileUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CheckProfileAvailableUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    @Singleton
    fun provideCreateTicketUseCase(
        ticketApiService: TicketApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateTicketUseCase(
        ticketApiService = ticketApiService,
        ioDispatcher = ioDispatcher,
    )

    @Provides
    @Singleton
    fun provideWithdrawTicketUseCase(
        ticketApiService: TicketApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = WithdrawTicketUseCase(
        ticketApiService = ticketApiService,
        ioDispatcher = ioDispatcher,
    )

    @Provides
    @Singleton
    fun provideTransferMoneyUseCase(
        paymentApiService: PaymentApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = TransferMoneyUseCase(
        paymentApiService = paymentApiService,
        ioDispatcher = ioDispatcher
    )
}