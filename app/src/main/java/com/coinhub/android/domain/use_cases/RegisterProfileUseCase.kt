package com.coinhub.android.domain.use_cases

import com.coinhub.android.data.repositories.AuthRepositoryImpl
import javax.inject.Inject

class RegisterProfileUseCase @Inject constructor(private val authRepositoryImpl: AuthRepositoryImpl) {
//    suspend operator fun invoke(createUserDto: CreateUserDto): AuthState<User> {
//        return try {
//            AuthState.Success(authRepositoryImpl.registerProfile(createUserDto = createUserDto))
//        } catch (e: Exception) {
//            AuthState.Error(e.message ?: "")
//        }
//    }

    suspend operator fun invoke() {}
}