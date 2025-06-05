package com.coinhub.android.domain.managers

import com.coinhub.android.common.enums.ThemeModeEnum
import com.coinhub.android.domain.repositories.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ThemeManger @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _themeMode = MutableStateFlow(ThemeModeEnum.SYSTEM)
    val themeMode = _themeMode.asStateFlow()

    init {
        scope.launch {
            _themeMode.update {
                preferenceDataStore.getThemeMode()
            }
        }
    }

    fun setThemeMode(themeMode: ThemeModeEnum) {
        scope.launch {
            preferenceDataStore.saveThemeMode(themeMode)
            _themeMode.update {
                themeMode
            }
        }
    }
}