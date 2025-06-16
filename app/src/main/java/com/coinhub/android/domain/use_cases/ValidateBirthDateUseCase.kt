package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import com.coinhub.android.utils.toLocalDate
import com.coinhub.android.utils.toMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ValidateBirthDateUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(birthDateInMillis: Long): Result {
        return withContext(defaultDispatcher) {
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val birthYear = birthDateInMillis.toLocalDate().year

            when {
                birthDateInMillis > LocalDate.now().toMillis() -> Result.Error("Birth date cannot be in the future")
                currentYear - birthYear < 18 -> Result.Error("You must be at least 18 years old")
                else -> Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
