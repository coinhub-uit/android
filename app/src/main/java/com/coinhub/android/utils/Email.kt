package com.coinhub.android.utils

fun validateEmail(email: String?): Boolean {
    return !email.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
