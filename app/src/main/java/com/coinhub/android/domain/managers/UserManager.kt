package com.coinhub.android.domain.managers

class UserManager {
    val userState get() = UserAppState.LOCKED
}

enum class UserAppState {
    LOADING,
    NOT_SIGNED_IN,
    SIGNED_IN,
    LOCKED,
    FAILED
}
