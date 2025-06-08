package com.coinhub.android.presentation.create_ticket.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.AvailablePlanModel
import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.common.components.SelectWithBottomSheet
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

        SelectWithBottomSheet(
            label = "Plan",
            selectedLabel = { plan: AvailablePlanModel -> "Rate: ${plan.rate}%, Days: ${plan.days}" },
            items = availablePlans,
            key = { it.planHistoryId },
            selectedItem = selectedAvailablePlan,
            getItemDescription = { plan -> "Rate: ${plan.rate}%, Days: ${plan.days}" },
            getItemLongDescription = { plan -> "Plan ID: ${plan.planHistoryId}" },
            onItemSelected = onSelectAvailablePlan,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectWithBottomSheet(
            label = "Method",
            selectedLabel = { method: MethodEnum -> method.description },
            items = MethodEnum.entries.toList(),
            key = { it.name },
            selectedItem = selectedMethod,
            getItemDescription = { method -> method.description },
            getItemLongDescription = { method -> method.longDescription },
            onItemSelected = onSelectMethod,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SelectWithBottomSheet(
            label = "Source",
            selectedLabel = { source: SourceModel -> "${source.id} (Balance: ${source.balance} VNĐ)" },
            items = sources,
            key = { it.id },
            selectedItem = selectedSourceId?.let { id -> sources.find { it.id == id } },
            getItemDescription = { source -> "${source.id} (Balance: ${source.balance} VNĐ)" },
            onItemSelected = { source -> onSelectSourceId(source.id) },
            modifier = Modifier.padding(bottom = 16.dp)
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