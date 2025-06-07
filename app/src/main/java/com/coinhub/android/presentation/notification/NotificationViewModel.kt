package com.coinhub.android.presentation.notification

import androidx.lifecycle.ViewModel
import com.coinhub.android.domain.models.NotificationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationModel>>(emptyList())
    val notifications = _notifications.asStateFlow()

    fun getNotifications() {
        // TODO: Heh
        _notifications.value = emptyList()
    }
}