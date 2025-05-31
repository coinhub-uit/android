package com.coinhub.android.presentation.top_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.presentation.top_up.components.TopUpSelectSource
import com.coinhub.android.presentation.top_up.components.TopUpSelectProvider
import com.coinhub.android.presentation.top_up.components.TopUpEnterAmount
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.presentation.navigation.AppNavDestinations

@Composable
fun TopUpScreen(
    onTopUpResult: (AppNavDestinations.TopUpResult) -> Unit,
    onBack: () -> Unit,
    viewModel: TopUpViewModel = hiltViewModel(),
) {
    val sourceId = viewModel.sourceId.collectAsStateWithLifecycle().value
    val isSourceBottomSheetVisible = viewModel.isSourceBottomSheetVisible.collectAsStateWithLifecycle().value
    val sourceModels = viewModel.sourceModels.collectAsStateWithLifecycle().value
    val topUpProvider = viewModel.topUpProvider.collectAsStateWithLifecycle().value
    val amountText = viewModel.amountText.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    TopUpScreen(
        selectedSourceId = sourceId,
        isSourceBottomSheetVisible = isSourceBottomSheetVisible,
        sourceModels = sourceModels,
        selectedProvider = topUpProvider,
        amountText = amountText,
        isFormValid = isFormValid,
        setShowBottomSheet = viewModel::setShowSourceBottomSheet,
        onSelectSource = viewModel::selectSource,
        onSelectProvider = viewModel::selectProvider,
        onAmountChange = viewModel::updateAmount,
        onPresetAmountClick = viewModel::setPresetAmount,
        onTopUpResult = { onTopUpResult(viewModel.getTopUpResult()) },
        onBack = onBack
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
    onTopUpResult: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopUpTopBar(
                onBack = onBack,
            )
        },
        floatingActionButton = {
            if (isFormValid) {
                FloatingActionButton(
                    onClick = onTopUpResult,
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
                onTopUpResult = {},
                onBack = {}
            )
        }
    }
}
