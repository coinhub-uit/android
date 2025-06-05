package com.coinhub.android.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coinhub.android.common.enums.ThemeModeEnum
import com.coinhub.android.domain.managers.ThemeManger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeManger: ThemeManger,
) : ViewModel() {
    val themeMode: StateFlow<ThemeModeEnum> = themeManger.themeMode

    fun setThemeMode(themeMode: ThemeModeEnum) {
        viewModelScope.launch {
            themeManger.setThemeMode(themeMode)
        }
    }
}
