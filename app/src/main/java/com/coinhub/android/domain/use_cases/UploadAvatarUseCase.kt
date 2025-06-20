package com.coinhub.android.domain.use_cases

import android.content.Context
import android.net.Uri
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.di.DefaultDispatcher
import com.coinhub.android.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class UploadAvatarUseCase @Inject constructor(
    private val userApiService: UserApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(userId: String, uri: Uri): Result {
        return withContext(ioDispatcher) {
            try {
                val multipartBody = uriToMultipartBody(uri)
                userApiService.uploadAvatar(userId, multipartBody)
                Result.Success
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Result.Error(
                    message = "Failed to upload avatar: ${e.message()}"
                )
            } catch (e: Exception) {
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private suspend fun uriToMultipartBody(uri: Uri): MultipartBody.Part {
        return withContext(defaultDispatcher) {
            try {
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(uri)
                val fileName = "avatar_${System.currentTimeMillis()}.jpg"
                val requestBody = inputStream?.readBytes()?.toRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("avatar", fileName, requestBody!!)
            } catch (
                e: CancellationException,
            ) {
                throw e
            } catch (
                e: Exception,
            ) {
                throw e
            }
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}