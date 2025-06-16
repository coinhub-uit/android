package com.coinhub.android.domain.use_cases

import com.coinhub.android.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ValidateSourceIdUseCase @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(sourceId: String): Result {
        return withContext(defaultDispatcher) {
            if (sourceId.isEmpty()) {
                Result.Error("Source cannot be empty")
            } else if (sourceId.length > 20) {
                Result.Error("Source cannot be longer than 20 characters")
            } else if (sourceId.contains(" ")) {
                Result.Error("Source cannot contain spaces")
                // TODO: @NTGNguyen - Add repo call to check if sourceId already exists
            } else {
                Result.Success
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}
