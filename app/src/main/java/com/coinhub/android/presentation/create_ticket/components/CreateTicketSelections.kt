package com.coinhub.android.presentation.create_ticket.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.common.components.RowSelectBottomSheet
import com.coinhub.android.ui.theme.CoinhubTheme
import java.math.BigInteger

@Composable
fun CreateTicketSelections(
    selectedAvailablePlan: AvailablePlanModel?,
    onSelectAvailablePlan: (AvailablePlanModel) -> Unit,
    availablePlans: List<AvailablePlanModel>,
    selectedMethod: MethodEnum? = null,
    onSelectMethod: (MethodEnum) -> Unit,
    sources: List<SourceModel>,
    selectedSourceId: String?,
    onSelectSourceId: (String) -> Unit,
) {
    Column {
        Text(
            text = "Selections",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectPlan(
            selectedAvailablePlan = selectedAvailablePlan,
            onAvailablePlanSelected = onSelectAvailablePlan,
            availablePlans = availablePlans,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectMethod(
            selectedMethod = selectedMethod,
            onMethodSelected = onSelectMethod,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectSource(
            selectedSourceId = selectedSourceId,
            onSelectSourceId = onSelectSourceId,
            sources = sources,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun SelectPlan(
    selectedAvailablePlan: AvailablePlanModel?,
    onAvailablePlanSelected: (AvailablePlanModel) -> Unit,
    availablePlans: List<AvailablePlanModel>,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = selectedAvailablePlan?.let { plan ->
            "Rate: ${plan.rate}%, Days: ${plan.days}"
        } ?: "Select Plan",
        onValueChange = {},
        readOnly = true,
        label = { Text("Plan") },
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isBottomSheetOpen = true
            },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Select Plan"
            )
        })

    if (isBottomSheetOpen) {
        RowSelectBottomSheet(
            items = availablePlans,
            key = { it.planHistoryId },
            selectedItem = selectedAvailablePlan,
            getDescription = { plan ->
                "Rate: ${plan.rate}%, Days: ${plan.days}"
            },
            getLongDescription = { plan ->
                "Plan ID: ${plan.planHistoryId}"
            },
            onItemSelected = { plan ->
                onAvailablePlanSelected(plan)
                isBottomSheetOpen = false
            },
        )
    }
}

@Composable
private fun SelectMethod(
    selectedMethod: MethodEnum?,
    onMethodSelected: (MethodEnum) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = selectedMethod?.description ?: "Select Method",
        onValueChange = {},
        readOnly = true,
        label = { Text("Method") },
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isBottomSheetOpen = true
            },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Select Method"
            )
        })

    if (isBottomSheetOpen) {
        RowSelectBottomSheet(
            items = MethodEnum.entries.toList(),
            key = { it.name },
            selectedItem = selectedMethod,
            getDescription = { method ->
                method.description
            },
            getLongDescription = { method ->
                method.longDescription
            },
            onItemSelected = { method ->
                onMethodSelected(method)
                isBottomSheetOpen = false
            },
        )
    }
}

@Composable
private fun SelectSource(
    selectedSourceId: String?,
    onSelectSourceId: (String) -> Unit,
    sources: List<SourceModel>,
    modifier: Modifier = Modifier,
) {
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = selectedSourceId?.let { id ->
            sources.find { it.id == id }?.let {
                "${it.id} (Balance: ${it.balance} VNĐ)"
            }
        } ?: "Select Source",
        onValueChange = {},
        readOnly = true,
        label = { Text("Source") },
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                isBottomSheetOpen = true
            },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Select Source"
            )
        }
    )

    if (isBottomSheetOpen) {
        RowSelectBottomSheet(
            items = sources,
            key = { it.id },
            selectedItem = selectedSourceId?.let { id ->
                sources.find { it.id == id }
            },
            getDescription = { source ->
                "${source.id} (Balance: ${source.balance} VNĐ)"
            },
            getLongDescription = { source ->
                "Source ID: ${source.id}, Balance: ${source.balance} VNĐ"
            },
            onItemSelected = { source ->
                onSelectSourceId(source.id)
                isBottomSheetOpen = false
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        CoinhubTheme {
            CreateTicketSelections(
                selectedAvailablePlan = AvailablePlanModel(1, 0.3F, 5, 30), onSelectAvailablePlan = {},
                availablePlans = listOf(
                    AvailablePlanModel(1, 0.3F, 5, 30),
                    AvailablePlanModel(2, 0.5F, 10, 60),
                    AvailablePlanModel(3, 0.7F, 15, 90),
                ),
                selectedMethod = MethodEnum.NR,
                onSelectMethod = {},
                sources = listOf(
                    SourceModel("1", BigInteger("5000000")),
                    SourceModel("2", BigInteger("3000000")),
                    SourceModel("3", BigInteger("7500000"))
                ),
                selectedSourceId = "1",
                onSelectSourceId = {}
            )
        }
    }
}
