package com.coinhub.android.presentation.states.auth

sealed class AuthState<out T> {
    data object Loading : AuthState<Nothing>()
    data class Success<T>(val data: T) : AuthState<T>()
    data class Error(val data: String) : AuthState<Nothing>()
}