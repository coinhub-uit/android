package com.coinhub.android.presentation.create_ticket

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.create_ticket.components.CreateTicketInputMoney
import com.coinhub.android.presentation.create_ticket.components.CreateTicketSelections
import com.coinhub.android.presentation.create_ticket.components.CreateTicketTopBar
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger
import java.time.ZonedDateTime

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    viewModel: CreateTicketViewModel = hiltViewModel(),
) {
    val amount = viewModel.amountText.collectAsStateWithLifecycle().value
    val amountError = viewModel.amountError.collectAsStateWithLifecycle().value
    val minimumAmount = viewModel.minimumAmount.collectAsStateWithLifecycle().value
    val selectedAvailablePlan = viewModel.selectedAvailablePlan.collectAsStateWithLifecycle().value
    val selectedMethod = viewModel.selectedMethod.collectAsStateWithLifecycle().value
    val selectedSourceId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    val availablePlans = viewModel.availablePlans.collectAsStateWithLifecycle().value
    val sourceModels = viewModel.sources.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isProcessing = viewModel.isProcessing.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    with(sharedTransitionScope) {
        CreateTicketScreen(
            minimumAmount = minimumAmount,
            amount = amount,
            amountError = amountError,
            selectedAvailablePlan = selectedAvailablePlan,
            selectedMethod = selectedMethod,
            selectedSourceId = selectedSourceId,
            availablePlans = availablePlans,
            sources = sourceModels,
            isFormValid = isFormValid,
            onAmountChange = viewModel::updateAmount,
            onSelectAvailablePlan = viewModel::selectPlan,
            onSelectMethod = viewModel::selectMethod,
            onSelectSourceId = viewModel::selectSourceId,
            onCreateTicket = { viewModel.createTicket(onBack) },
            isLoading = isLoading,
            isProcessing = isProcessing,
            onBack = onBack,
            modifier = Modifier.sharedBounds(
                animatedVisibilityScope = animatedVisibilityScope, sharedContentState = rememberSharedContentState(
                    key = "createTicket",
                )
            )
        )
    }
}

@Composable
private fun CreateTicketScreen(
    modifier: Modifier = Modifier,
    minimumAmount: Long,
    amount: String,
    amountError: String?,
    selectedAvailablePlan: AvailablePlanModel?,
    selectedMethod: MethodEnum?,
    selectedSourceId: String?,
    availablePlans: List<AvailablePlanModel>,
    sources: List<SourceModel>,
    isFormValid: Boolean,
    onAmountChange: (String) -> Unit,
    onSelectAvailablePlan: (AvailablePlanModel) -> Unit,
    onSelectMethod: (MethodEnum) -> Unit,
    onSelectSourceId: (String) -> Unit,
    onCreateTicket: () -> Unit,
    onBack: () -> Unit,
    isLoading: Boolean,
    isProcessing: Boolean,
) {
    Scaffold(
        topBar = {
            CreateTicketTopBar(onBack = onBack)
        }, floatingActionButton = {
            if (isFormValid && !isLoading && !isProcessing) {
                FloatingActionButton(onClick = onCreateTicket) {
                    Icon(
                        imageVector = Icons.Filled.Check, contentDescription = "Create Ticket"
                    )
                }
            }
        }, modifier = modifier
    ) { innerPadding ->
        if (isLoading || isProcessing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            CreateTicketInputMoney(
                minimumAmount = minimumAmount, amount = amount,
                amountError = amountError, onAmountChange = onAmountChange,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CreateTicketSelections(
                selectedAvailablePlan = selectedAvailablePlan,
                onSelectAvailablePlan = onSelectAvailablePlan,
                availablePlans = availablePlans,
                selectedMethod = selectedMethod,
                onSelectMethod = onSelectMethod,
                sources = sources,
                selectedSourceId = selectedSourceId,
                onSelectSourceId = onSelectSourceId,
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun CreateTicketScreenPreview() {
    CoinhubTheme {
        Surface {
            CreateTicketScreen(
                minimumAmount = 1_000_000L,
                amount = "1000",
                amountError = null,
                selectedAvailablePlan = AvailablePlanModel(1, 5.5f, 1, 30),
                selectedMethod = MethodEnum.PIR,
                selectedSourceId = "1",
                availablePlans = listOf(
                    AvailablePlanModel(1, 5.5f, 1, 30),
                    AvailablePlanModel(2, 7.2f, 2, 60),
                    AvailablePlanModel(3, 8.5f, 3, 90)
                ),
                sources = listOf(
                    SourceModel(
                        "1",
                        BigInteger("5000000"),
                        openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                    ),
                    SourceModel(
                        "2",
                        BigInteger("3000000"),
                        openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                    ),
                    SourceModel(
                        "3",
                        BigInteger("7500000"),
                        openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                    )
                ),
                isFormValid = true,
                onAmountChange = {},
                onSelectAvailablePlan = {},
                onSelectMethod = {},
                onSelectSourceId = {},
                onCreateTicket = {},
                isLoading = false,
                isProcessing = false,
                onBack = {})
        }
    }
}
