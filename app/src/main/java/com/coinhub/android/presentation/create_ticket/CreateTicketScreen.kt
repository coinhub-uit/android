package com.coinhub.android.presentation.create_ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.AvailablePlanModel
import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onMain: () -> Unit,
    viewModel: CreateTicketViewModel = hiltViewModel()
) {
    val amountText = viewModel.amountText.collectAsStateWithLifecycle().value
    val selectedPlan = viewModel.selectedPlan.collectAsStateWithLifecycle().value
    val selectedMethod = viewModel.selectedMethod.collectAsStateWithLifecycle().value
    val selectedSourceId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    val isPlanBottomSheetVisible = viewModel.isPlanBottomSheetVisible.collectAsStateWithLifecycle().value
    val isMethodBottomSheetVisible = viewModel.isMethodBottomSheetVisible.collectAsStateWithLifecycle().value
    val isSourceBottomSheetVisible = viewModel.isSourceBottomSheetVisible.collectAsStateWithLifecycle().value
    val availablePlans = viewModel.availablePlans.collectAsStateWithLifecycle().value
    val sourceModels = viewModel.sourceModels.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value
    
    CreateTicketScreen(
        amountText = amountText,
        selectedPlan = selectedPlan,
        selectedMethod = selectedMethod,
        selectedSourceId = selectedSourceId,
        isPlanBottomSheetVisible = isPlanBottomSheetVisible,
        isMethodBottomSheetVisible = isMethodBottomSheetVisible,
        isSourceBottomSheetVisible = isSourceBottomSheetVisible,
        availablePlans = availablePlans,
        sourceModels = sourceModels,
        isFormValid = isFormValid,
        onAmountChange = viewModel::updateAmount,
        onSelectPlan = viewModel::selectPlan,
        onSelectMethod = viewModel::selectMethod,
        onSelectSource = viewModel::selectSource,
        setShowPlanBottomSheet = viewModel::setShowPlanBottomSheet,
        setShowMethodBottomSheet = viewModel::setShowMethodBottomSheet,
        setShowSourceBottomSheet = viewModel::setShowSourceBottomSheet,
        onCreateTicket = {
            viewModel.createTicket()
            onMain()
        },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTicketScreen(
    amountText: String,
    selectedPlan: AvailablePlanModel?,
    selectedMethod: MethodEnum?,
    selectedSourceId: String?,
    isPlanBottomSheetVisible: Boolean,
    isMethodBottomSheetVisible: Boolean,
    isSourceBottomSheetVisible: Boolean,
    availablePlans: List<AvailablePlanModel>,
    sourceModels: List<SourceModel>,
    isFormValid: Boolean,
    onAmountChange: (String) -> Unit,
    onSelectPlan: (AvailablePlanModel) -> Unit,
    onSelectMethod: (MethodEnum) -> Unit,
    onSelectSource: (String) -> Unit,
    setShowPlanBottomSheet: (Boolean) -> Unit,
    setShowMethodBottomSheet: (Boolean) -> Unit,
    setShowSourceBottomSheet: (Boolean) -> Unit,
    onCreateTicket: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Ticket") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
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
            // Input Money Section
            Text(
                text = "Input Money",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Minimum amount: 1,000,000 VND",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            OutlinedTextField(
                value = amountText,
                onValueChange = onAmountChange,
                label = { Text("Amount (VND)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Selection Section
            Text(
                text = "Plan Selection",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Select Plan
            Text(
                text = "Select Plan",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedCard(
                onClick = { setShowPlanBottomSheet(true) },
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
                        text = selectedPlan?.let { plan ->
                            "Rate: ${plan.rate}%, Days: ${plan.days}"
                        } ?: "Select Plan"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select Plan"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Select Method
            Text(
                text = "Select Method",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedCard(
                onClick = { setShowMethodBottomSheet(true) },
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
                        text = selectedMethod?.description ?: "Select Method"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select Method"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Select Source
            Text(
                text = "Select Source",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedCard(
                onClick = { setShowSourceBottomSheet(true) },
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
                            sourceModels.find { it.id == id }?.let {
                                "${it.id} (Balance: ${it.balance} VNĐ)"
                            }
                        } ?: "Select Source"
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select Source"
                    )
                }
            }
            
            // Bottom Sheets
            if (isPlanBottomSheetVisible) {
                PlanBottomSheet(
                    availablePlans = availablePlans,
                    selectedPlan = selectedPlan,
                    onPlanSelected = onSelectPlan,
                    onDismissRequest = { setShowPlanBottomSheet(false) }
                )
            }
            
            if (isMethodBottomSheetVisible) {
                MethodBottomSheet(
                    selectedMethod = selectedMethod,
                    onMethodSelected = onSelectMethod,
                    onDismissRequest = { setShowMethodBottomSheet(false) }
                )
            }
            
            if (isSourceBottomSheetVisible) {
                SourceBottomSheet(
                    sourceModels = sourceModels,
                    selectedSourceId = selectedSourceId,
                    onSourceSelected = onSelectSource,
                    onDismissRequest = { setShowSourceBottomSheet(false) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanBottomSheet(
    availablePlans: List<AvailablePlanModel>,
    selectedPlan: AvailablePlanModel?,
    onPlanSelected: (AvailablePlanModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select Plan",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn {
                items(availablePlans) { plan ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onPlanSelected(plan)
                                onDismissRequest()
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Rate: ${plan.rate}%",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Days: ${plan.days}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (selectedPlan?.planHistoryId == plan.planHistoryId) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    if (plan != availablePlans.last()) {
                        HorizontalDivider()
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MethodBottomSheet(
    selectedMethod: MethodEnum?,
    onMethodSelected: (MethodEnum) -> Unit,
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select Method",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn {
                items(MethodEnum.entries.toTypedArray()) { method ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onMethodSelected(method)
                                onDismissRequest()
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = method.description,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = when (method) {
                                    MethodEnum.NR -> "The interest is will be given to the user at the end of the plan, and the ticket will be closed."
                                    MethodEnum.PR -> "Principal will be rolled over to the next plan, and the interest will be given to the user at the end of the plan."
                                    MethodEnum.PIR -> "Principal and interest will be rolled over to the next plan. No interest will be given to the user at the end of the plan until user withdraw."
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (selectedMethod == method) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    if (method != MethodEnum.entries.last()) {
                        HorizontalDivider()
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SourceBottomSheet(
    sourceModels: List<SourceModel>,
    selectedSourceId: String?,
    onSourceSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select Source",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn {
                items(sourceModels) { source ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSourceSelected(source.id)
                                onDismissRequest()
                            }
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
                    
                    if (source != sourceModels.last()) {
                        HorizontalDivider()
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun CreateTicketScreenPreview() {
    CoinhubTheme {
        Surface {
            var amountText by remember { mutableStateOf("") }
            var selectedPlan by remember { mutableStateOf<AvailablePlanModel?>(null) }
            var selectedMethod by remember { mutableStateOf<MethodEnum?>(null) }
            var selectedSourceId by remember { mutableStateOf<String?>(null) }
            var isPlanBottomSheetVisible by remember { mutableStateOf(false) }
            var isMethodBottomSheetVisible by remember { mutableStateOf(false) }
            var isSourceBottomSheetVisible by remember { mutableStateOf(false) }
            
            val availablePlans = listOf(
                AvailablePlanModel(1, 5.5f, 101, 30),
                AvailablePlanModel(2, 7.2f, 102, 60),
                AvailablePlanModel(3, 8.5f, 103, 90)
            )
            
            val sourceModels = listOf(
                SourceModel("1", BigInteger("5000000")),
                SourceModel("2", BigInteger("3000000")),
                SourceModel("3", BigInteger("7500000"))
            )
            
            CreateTicketScreen(
                amountText = amountText,
                selectedPlan = selectedPlan,
                selectedMethod = selectedMethod,
                selectedSourceId = selectedSourceId,
                isPlanBottomSheetVisible = isPlanBottomSheetVisible,
                isMethodBottomSheetVisible = isMethodBottomSheetVisible,
                isSourceBottomSheetVisible = isSourceBottomSheetVisible,
                availablePlans = availablePlans,
                sourceModels = sourceModels,
                isFormValid = false,
                onAmountChange = { amountText = it },
                onSelectPlan = { selectedPlan = it },
                onSelectMethod = { selectedMethod = it },
                onSelectSource = { selectedSourceId = it },
                setShowPlanBottomSheet = { isPlanBottomSheetVisible = it },
                setShowMethodBottomSheet = { isMethodBottomSheetVisible = it },
                setShowSourceBottomSheet = { isSourceBottomSheetVisible = it },
                onCreateTicket = { },
                onBack = { }
            )
        }
    }
}
