package com.coinhub.android.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationModel>>(emptyList())
    val notifications = _notifications.asStateFlow()


    fun fetch() {
        viewModelScope.launch {
            _notifications.value = userRepository.getUserNotification(
                authRepository.getCurrentUserId(),
                refresh = false
            )
        }
    }

}
