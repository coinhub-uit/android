package com.coinhub.android.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.coinhub.android.utils.copyToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    fun copySourceIdToClipboard(context: Context, sourceId: String) {
        copyToClipboard(context, sourceId, label = "Source ID")
        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
    }
}