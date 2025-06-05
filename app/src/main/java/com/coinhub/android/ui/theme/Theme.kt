package com.coinhub.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.coinhub.android.common.enums.ThemeModeEnum

@Composable
fun CoinhubTheme(
    themeMode: ThemeModeEnum = ThemeModeEnum.SYSTEM,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    
    val darkTheme = when (themeMode) {
        ThemeModeEnum.LIGHT -> false
        ThemeModeEnum.DARK -> true
        ThemeModeEnum.SYSTEM -> isSystemInDarkTheme()
    }
    
    MaterialTheme(
        colorScheme = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
            context
        ),
        typography = Typography,
        content = content
    )
}
