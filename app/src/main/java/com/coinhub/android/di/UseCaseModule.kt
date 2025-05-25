package com.coinhub.android.di

import com.coinhub.android.domain.use_cases.ValidateConfirmPasswordUseCase
import com.coinhub.android.domain.use_cases.ValidateEmailUseCase
import com.coinhub.android.domain.use_cases.ValidatePasswordUseCase
import com.coinhub.android.domain.use_cases.auth.ValidatePasswordUseCaseImpl
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
    abstract fun provideValidatePasswordUseCase(impl: ValidatePasswordUseCaseImpl): ValidatePasswordUseCase

    @Binds
    abstract fun provideValidateConfirmPasswordUseCase(impl: ValidateConfirmPasswordUseCase): ValidateConfirmPasswordUseCase
}