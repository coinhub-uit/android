package com.coinhub.android.di

import com.coinhub.android.domain.use_cases.HandleResultOnSignInWithGoogleUseCase
import com.coinhub.android.domain.use_cases.RegisterProfileUseCase
import com.coinhub.android.domain.use_cases.SignInWithCredentialUseCase
import com.coinhub.android.domain.use_cases.SignUpWithCredentialUseCase
import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideValidateEmailUseCase(impl: ValidateEmailUseCase): ValidateEmailUseCase

    @Binds
    abstract fun provideValidatePasswordUseCase(impl: ValidatePasswordUseCase): ValidatePasswordUseCase

    @Binds
    abstract fun provideValidateConfirmPasswordUseCase(impl: ValidateConfirmPasswordUseCase): ValidateConfirmPasswordUseCase

    @Binds
    abstract fun provideSignInWithCredentialUseCase(impl: SignInWithCredentialUseCase): SignInWithCredentialUseCase

    @Binds
    abstract fun provideSignUpWithCredentialUseCase(impl: SignUpWithCredentialUseCase): SignUpWithCredentialUseCase

    @Binds
    abstract fun provideHandleResultOnSignInWithGoogleUseCase(impl: HandleResultOnSignInWithGoogleUseCase): HandleResultOnSignInWithGoogleUseCase

    @Binds
    abstract fun provideRegisterProfileUseCase(impl: RegisterProfileUseCase): RegisterProfileUseCase
}