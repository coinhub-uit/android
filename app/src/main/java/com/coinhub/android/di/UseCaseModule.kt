package com.coinhub.android.di

import android.content.Context
import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PaymentRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.use_cases.ChangeCredentialUseCase
import com.coinhub.android.domain.use_cases.CheckProfileAvailableUseCase
import com.coinhub.android.domain.use_cases.CheckUserSignedInUseCase
import com.coinhub.android.domain.use_cases.CloseSourceUseCase
import com.coinhub.android.domain.use_cases.CreateProfileUseCase
import com.coinhub.android.domain.use_cases.CreateSourceUseCase
import com.coinhub.android.domain.use_cases.CreateTicketUseCase
import com.coinhub.android.domain.use_cases.CreateTopUpUseCase
import com.coinhub.android.domain.use_cases.DeleteAccountUseCase
import com.coinhub.android.domain.use_cases.DeleteAvatarUseCase
import com.coinhub.android.domain.use_cases.GetTopUpUseCase
import com.coinhub.android.domain.use_cases.RegisterDeviceUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.TransferMoneyUseCase
import com.coinhub.android.domain.use_cases.UpdateProfileUseCase
import com.coinhub.android.domain.use_cases.UploadAvatarUseCase
import com.coinhub.android.domain.use_cases.ValidateAmountCreateTicketUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.domain.use_cases.ValidatePinUseCase
import com.coinhub.android.domain.use_cases.ValidateSourceIdUseCase
import com.coinhub.android.domain.use_cases.WithdrawTicketUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

// TODO: Maybe scoped to view model
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideValidateEmailUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateEmailUseCase(defaultDispatcher)

    @Provides
    fun provideValidatePasswordUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidatePasswordUseCase(defaultDispatcher)

    @Provides
    fun provideValidateConfirmPasswordUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateConfirmPasswordUseCase(defaultDispatcher)

    @Provides
    fun provideValidatePinUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidatePinUseCase(defaultDispatcher)

    @Provides
    fun provideValidateAmountCreateTicketUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ) = ValidateAmountCreateTicketUseCase(defaultDispatcher)

    @Provides
    fun provideValidateSourceIdUseCase(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        sourceApiService: SourceApiService,
    ) = ValidateSourceIdUseCase(sourceApiService,defaultDispatcher)

    @Provides
    fun provideSignInWithCredentialUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) =
        SignInWithCredentialUseCase(
            authRepository = authRepository,
            preferenceDataStore = preferenceDataStore
        )

    @Provides
    fun provideSignUpWithCredentialUseCase(
        authRepository: AuthRepository, preferenceDataStore: PreferenceDataStore,
    ) = SignUpWithCredentialUseCase(
        authRepository = authRepository,
        preferenceDataStore = preferenceDataStore
    )

    @Provides
    fun provideHandleResultOnSignInWithGoogleUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) =
        SignInWithGoogleUseCase(
            authRepository = authRepository,
            preferenceDataStore = preferenceDataStore
        )

    @Provides
    fun provideCheckUserSignedInUseCase(
        authRepository: AuthRepository,
        preferenceDataStore: PreferenceDataStore,
    ) = CheckUserSignedInUseCase(
        authRepository = authRepository,
        preferenceDataStore = preferenceDataStore
    )

    @Provides
    fun provideCreateTopUpUseCase(
        paymentRepository: PaymentRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateTopUpUseCase(
        paymentRepository = paymentRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideGetTopUpUseCase(
        paymentRepository: PaymentRepository,
    ) = GetTopUpUseCase(
        paymentRepository = paymentRepository,
    )

    @Provides
    fun provideCreateSourceUseCase(
        sourceApiService: SourceApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateSourceUseCase(
        sourceApiService = sourceApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideCheckUserRegisterProfileUseCase(
        userRepository: UserRepositoryImpl,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CheckProfileAvailableUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideCreateTicketUseCase(
        ticketApiService: TicketApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CreateTicketUseCase(
        ticketApiService = ticketApiService,
        ioDispatcher = ioDispatcher,
    )

    @Provides
    fun provideWithdrawTicketUseCase(
        ticketApiService: TicketApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = WithdrawTicketUseCase(
        ticketApiService = ticketApiService,
        ioDispatcher = ioDispatcher,
    )

    @Provides
    fun provideTransferMoneyUseCase(
        paymentApiService: PaymentApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = TransferMoneyUseCase(
        paymentApiService = paymentApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideRegisterDeviceUseCase(
        userRepository: UserRepositoryImpl,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = RegisterDeviceUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun uploadAvatarUseCase(
        userApiService: UserApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        @ApplicationContext context: Context,
    ) = UploadAvatarUseCase(
        userApiService = userApiService,
        ioDispatcher = ioDispatcher,
        defaultDispatcher = defaultDispatcher,
        context = context
    )

    @Provides
    fun updatePartialProfileUseCase(
        userApiService: UserApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = UpdateProfileUseCase(
        userApiService = userApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideCreateProfileUseCase(
        userRepository: UserRepositoryImpl,
        authRepository: AuthRepository,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        preferenceDataStore: PreferenceDataStore,
    ) = CreateProfileUseCase(
        userRepository = userRepository,
        authRepository = authRepository,
        ioDispatcher = ioDispatcher,
        preferenceDataStore = preferenceDataStore
    )

    @Provides
    fun provideDeleteAccountUseCase(
        userApiService: UserApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = DeleteAccountUseCase(
        userApiService = userApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideDeleteAvatarUseCase(
        userApiService: UserApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = DeleteAvatarUseCase(
        userApiService = userApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideCloseSourceUseCase(
        sourceApiService: SourceApiService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = CloseSourceUseCase(
        sourceApiService = sourceApiService,
        ioDispatcher = ioDispatcher
    )

    @Provides
    fun provideChangeCredentialUseCase(
        supabaseService: SupabaseService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ) = ChangeCredentialUseCase(
        supabaseService = supabaseService,
        ioDispatcher = ioDispatcher
    )
}