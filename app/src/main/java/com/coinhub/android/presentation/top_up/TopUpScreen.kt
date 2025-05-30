package com.coinhub.android.presentation.top_up

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.presentation.top_up.components.TopUpSelectSource
import com.coinhub.android.presentation.top_up.components.TopUpSelectProvider
import com.coinhub.android.presentation.top_up.components.TopUpEnterAmount
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TopUpProviderEnum

@Composable
fun TopUpScreen(
    navigateToTopUpResult: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: TopUpViewModel = hiltViewModel(),
) {
    val selectedSourceId by viewModel.selectedSourceId
    val isSourceBottomSheetVisible by viewModel.isSourceBottomSheetVisible
    val sourceModels by viewModel.sourceModels
    val selectedProvider by viewModel.selectedProvider
    val amountText by viewModel.amountText
    val isFormValid by viewModel.isFormValid

    TopUpScreen(
        selectedSourceId = selectedSourceId,
        isSourceBottomSheetVisible = isSourceBottomSheetVisible,
        sourceModels = sourceModels,
        selectedProvider = selectedProvider,
        amountText = amountText,
        isFormValid = isFormValid,
        setShowBottomSheet = viewModel::setShowSourceBottomSheet,
        onSelectSource = viewModel::selectSource,
        onSelectProvider = viewModel::selectProvider,
        onAmountChange = viewModel::updateAmount,
        onPresetAmountClick = viewModel::setPresetAmount,
        navigateToTopUpResult = navigateToTopUpResult,
        navigateUp = navigateUp
    )
}

@Composable
private fun TopUpScreen(
    selectedSourceId: String?,
    isSourceBottomSheetVisible: Boolean,
    sourceModels: List<SourceModel>,
    selectedProvider: TopUpProviderEnum?,
    amountText: String,
    isFormValid: Boolean,
    setShowBottomSheet: (Boolean) -> Unit,
    onSelectSource: (String) -> Unit,
    onSelectProvider: (TopUpProviderEnum) -> Unit,
    onAmountChange: (String) -> Unit,
    onPresetAmountClick: (String) -> Unit,
    navigateToTopUpResult: () -> Unit,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopUpTopBar(
                navigateUp = navigateUp,
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
            TopUpSelectSource(
                selectedSourceId = selectedSourceId,
                sourceModels = sourceModels,
                isBottomSheetVisible = isSourceBottomSheetVisible,
                setShowBottomSheet = setShowBottomSheet,
                onSelectSource = onSelectSource
            )

            Spacer(modifier = Modifier.height(24.dp))

            TopUpSelectProvider(
                selectedProvider = selectedProvider,
                onSelectProvider = onSelectProvider
            )

            Spacer(modifier = Modifier.height(24.dp))

            TopUpEnterAmount(
                amountText = amountText,
                onAmountChange = onAmountChange,
                onPresetAmountClick = onPresetAmountClick
            )
        }
    }
}

@PreviewLightDark
@Composable
fun TopUpScreenPreview() {
    CoinhubTheme {
        Surface {
            TopUpScreen(
                selectedSourceId = "sourceId",
                isSourceBottomSheetVisible = false,
                sourceModels = listOf(),
                selectedProvider = null,
                amountText = "",
                isFormValid = false,
                setShowBottomSheet = {},
                onSelectSource = {},
                onSelectProvider = {},
                onAmountChange = {},
                onPresetAmountClick = {},
                navigateToTopUpResult = {},
                navigateUp = {}
            )
        }
    }
}
