package com.coinhub.android.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.common.enums.ThemeModeEnum

// TODO: AI gen this for me and if I have time, I will refactor this
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val selectedThemeMode by viewModel.themeMode.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Theme",
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            ThemeSelectionCard(
                selectedThemeMode = selectedThemeMode,
                onThemeModeSelected = { viewModel.setThemeMode(it) }
            )
        }
    }
}

@Composable
fun ThemeSelectionCard(
    selectedThemeMode: ThemeModeEnum,
    onThemeModeSelected: (ThemeModeEnum) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "App theme",
                    style = typography.titleMedium
                )
            }

            ThemeOption(
                title = "Light",
                selected = selectedThemeMode == ThemeModeEnum.LIGHT,
                onClick = { onThemeModeSelected(ThemeModeEnum.LIGHT) }
            )
            
            ThemeOption(
                title = "Dark",
                selected = selectedThemeMode == ThemeModeEnum.DARK,
                onClick = { onThemeModeSelected(ThemeModeEnum.DARK) }
            )
            
            ThemeOption(
                title = "System default",
                selected = selectedThemeMode == ThemeModeEnum.SYSTEM,
                onClick = { onThemeModeSelected(ThemeModeEnum.SYSTEM) }
            )
        }
    }
}

@Composable
fun ThemeOption(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = title,
            style = typography.bodyLarge,
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable(onClick = onClick)
        )
    }
}
