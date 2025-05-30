package com.coinhub.android.presentation.top_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.presentation.top_up.components.TopUpViewModel
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpScreen(
    navigateToTopUpResult: () -> Unit,
    viewModel: TopUpViewModel = hiltViewModel()
) {
    val selectedSourceId by viewModel.selectedSourceId
    val isSourceBottomSheetVisible by viewModel.isSourceBottomSheetVisible
    val availableSources by viewModel.availableSources
    val selectedProvider by viewModel.selectedProvider
    val amountText by viewModel.amountText
    val isFormValid by viewModel.isFormValid

    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopUpTopBar(
                navigateUp = navigateToTopUpResult,
            )
        },
        floatingActionButton = {
            if (isFormValid) {
                FloatingActionButton(
                    onClick = navigateToTopUpResult
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Source Selection Section
            Text(
                text = "Select Source",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedCard(
                onClick = { viewModel.showSourceBottomSheet() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedSourceId?.let { id ->
                            availableSources.find { it.id == id }?.let { 
                                "Source ID: ${it.id} (Balance: ${it.balance} VNĐ)"
                            }
                        } ?: "Select Source"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select Source"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Provider Selection Section
            Text(
                text = "Select Provider",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TopUpProviderEnum.entries.toTypedArray()) { provider ->
                    FilterChip(
                        selected = selectedProvider == provider,
                        onClick = { viewModel.selectProvider(provider) },
                        label = { Text(provider.name) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Amount Input Section
            Text(
                text = "Enter Amount",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = amountText,
                onValueChange = { viewModel.updateAmount(it) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix = { Text("VNĐ") },
                singleLine = true,
                placeholder = { Text("0") }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Predefined Amounts Section
            Text(
                text = "Quick Amount Selection",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            val presetAmounts = listOf(
                "50.000",
                "100.000",
                "200.000",
                "500.000",
                "1.000.000",
                "2.000.000"
            )
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(presetAmounts) { amount ->
                    ElevatedCard(
                        onClick = { viewModel.setPresetAmount(amount) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$amount VNĐ",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
    
    // Bottom Sheet for Source Selection
    if (isSourceBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideSourceBottomSheet() },
            sheetState = bottomSheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Source",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                LazyColumn {
                    items(availableSources) { source ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectSource(source.id) }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Source ID: ${source.id} (Balance: ${source.balance} VNĐ)",
                                modifier = Modifier.weight(1f)
                            )
                            
                            if (selectedSourceId == source.id) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        
                        if (source != availableSources.last()) {
                            HorizontalDivider()
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TopUpScreenPreview() {
    CoinhubTheme {
        Surface {
            TopUpScreen(
                navigateToTopUpResult = {},
            )
        }
    }
}
