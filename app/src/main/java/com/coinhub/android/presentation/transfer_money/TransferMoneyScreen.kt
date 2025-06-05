package com.coinhub.android.presentation.transfer_money

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.presentation.transfer_money.components.TransferMoneyEnterAmount
import com.coinhub.android.presentation.transfer_money.components.TransferMoneyReceiptSource
import com.coinhub.android.presentation.transfer_money.components.TransferMoneySelectSource
import com.coinhub.android.presentation.transfer_money.components.TransferMoneyTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransferMoneyScreen(
    onBack: () -> Unit,
    viewModel: TransferMoneyViewModel = hiltViewModel(),
) {
    val selectedSourceId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    val sources = viewModel.sources.collectAsStateWithLifecycle().value
    val receiptSourceId = viewModel.receiptSourceId.collectAsStateWithLifecycle().value
    val receiptUser = viewModel.receiptUser.collectAsStateWithLifecycle().value
    val amount = viewModel.amountText.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    with(sharedTransitionScope) {
        TransferMoneyScreen(
            selectedSourceId = selectedSourceId,
            sources = sources,
            receiptSourceId = receiptSourceId,
            receiptUser = receiptUser,
            amount = amount,
            isFormValid = isFormValid,
            isLoading = isLoading,
            onSelectSource = viewModel::selectSource,
            onReceiptSourceIdChange = viewModel::updateReceiptSourceId,
            onAmountChange = viewModel::updateAmount,
            onTransfer = { viewModel.transferMoney(onBack) },
            onBack = onBack,
            modifier = Modifier.sharedBounds(
                animatedVisibilityScope = animatedVisibilityScope, sharedContentState = rememberSharedContentState(
                    key = "transferMoney",
                )
            ),
        )
    }
}

@Composable
private fun TransferMoneyScreen(
    modifier: Modifier = Modifier,
    selectedSourceId: String?,
    sources: List<SourceModel>,
    receiptSourceId: String,
    receiptUser: UserModel?,
    amount: String,
    isFormValid: Boolean,
    isLoading: Boolean,
    onSelectSource: (String) -> Unit,
    onReceiptSourceIdChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onTransfer: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TransferMoneyTopBar(onBack = onBack)
        }, floatingActionButton = {
            AnimatedVisibility(
                visible = isFormValid && !isLoading,
            ) {
                FloatingActionButton(onClick = onTransfer) {
                    Icon(
                        imageVector = Icons.Filled.Check, contentDescription = "Transfer Money"
                    )
                }
            }
        }, modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TransferMoneySelectSource(
                    selectedSourceId = selectedSourceId,
                    sources = sources,
                    onSelectSource = onSelectSource,
                )

                Spacer(modifier = Modifier.height(24.dp))

                TransferMoneyReceiptSource(
                    receiptSourceId = receiptSourceId,
                    onReceiptSourceIdChange = onReceiptSourceIdChange,
                    receiptUser = receiptUser
                )

                Spacer(modifier = Modifier.height(24.dp))

                TransferMoneyEnterAmount(
                    amount = amount, onAmountChange = onAmountChange
                )
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun TransferMoneyScreenPreview() {
    CoinhubTheme {
        Surface {
            TransferMoneyScreen(
                selectedSourceId = "1",
                sources = listOf(
                    SourceModel("1", BigInteger("5000000")),
                    SourceModel("2", BigInteger("3000000")),
                    SourceModel("3", BigInteger("7500000"))
                ),
                receiptSourceId = "123",
                receiptUser = UserModel(
                    id = Uuid.random(),
                    fullName = "Nguyen Van A",
                    citizenId = "123456789",
                    birthDate = java.time.LocalDate.now(),
                    avatar = "https://example.com/avatar.png",
                    address = "123 Street, City",
                    createdAt = ZonedDateTime.now(),
                    deletedAt = null
                ),
                amount = "1000000",
                isFormValid = true,
                isLoading = false,
                onSelectSource = {},
                onReceiptSourceIdChange = {},
                onAmountChange = {},
                onTransfer = {},
                onBack = {})
        }
    }
}
