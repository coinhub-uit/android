package com.coinhub.android.presentation.notification

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.data.api_services.NotificationApiService
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.data.repositories.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userRepository: UserRepositoryImpl,
    private val authRepository: AuthRepository,
    private val notificationApiService: NotificationApiService,
) : ViewModel() {
    val notifications = mutableStateListOf<NotificationModel>()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun fetch() {
        viewModelScope.launch {
            _isLoading.value = true
            if (notifications.isNotEmpty()) {
                notifications.clear()
            }
            notifications.addAll(
                userRepository.getUserNotification(
                    authRepository.getCurrentUserId(), refresh = true
                )
            )
            _isLoading.value = false
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun markAsRead(notificationId: Uuid) {
        viewModelScope.launch {
            try {
                notificationApiService.readById(notificationId)
                notifications.indexOfFirst { it.id == notificationId }.takeIf { it != -1 }?.let { idx ->
                        notifications[idx] = notifications[idx].copy(isRead = true)
                    }
            } catch (e: HttpException) {
                _toastMessage.emit(e.message())
            }
        }
    }
}
