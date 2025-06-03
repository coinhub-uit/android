package com.coinhub.android.presentation.create_ticket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.presentation.create_ticket.components.CreateTicketInputMoney
import com.coinhub.android.presentation.create_ticket.components.CreateTicketSelections
import com.coinhub.android.presentation.create_ticket.components.CreateTicketTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onMain: () -> Unit,
    viewModel: CreateTicketViewModel = hiltViewModel(),
) {
    val amount = viewModel.amountText.collectAsStateWithLifecycle().value
    val selectedAvailablePlan = viewModel.selectedAvailablePlan.collectAsStateWithLifecycle().value
    val selectedMethod = viewModel.selectedMethod.collectAsStateWithLifecycle().value
    val selectedSourceId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    val availablePlans = viewModel.availablePlans.collectAsStateWithLifecycle().value
    val sourceModels = viewModel.sources.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    CreateTicketScreen(
        amount = amount,
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
        onCreateTicket = {
            viewModel.createTicket()
            onMain()
        },
        onBack = onBack
    )
}

@Composable
private fun CreateTicketScreen(
    amount: String,
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
) {
    Scaffold(
        topBar = {
            CreateTicketTopBar(onBack = onBack)
        },
        floatingActionButton = {
            if (isFormValid) {
                FloatingActionButton(onClick = onCreateTicket) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Create Ticket"
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
                .verticalScroll(rememberScrollState())
        ) {

            CreateTicketInputMoney(amount = amount, onAmountChange = onAmountChange)

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
                amount = "1000",
                selectedAvailablePlan = AvailablePlanModel(1, 5.5f, 1, 30),
                selectedMethod = MethodEnum.PIR,
                selectedSourceId = "1",
                availablePlans = listOf(
                    AvailablePlanModel(1, 5.5f, 1, 30),
                    AvailablePlanModel(2, 7.2f, 2, 60),
                    AvailablePlanModel(3, 8.5f, 3, 90)
                ),
                sources = listOf(
                    SourceModel("1", BigInteger("5000000")),
                    SourceModel("2", BigInteger("3000000")),
                    SourceModel("3", BigInteger("7500000"))
                ),
                isFormValid = true,
                onAmountChange = {},
                onSelectAvailablePlan = {},
                onSelectMethod = {},
                onSelectSourceId = {},
                onCreateTicket = {},
                onBack = {}
            )
        }
    }
}
