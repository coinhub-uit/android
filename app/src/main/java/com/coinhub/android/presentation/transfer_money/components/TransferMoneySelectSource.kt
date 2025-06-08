package com.coinhub.android.presentation.transfer_money.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.common.components.SelectWithBottomSheet

@Composable
fun TransferMoneySelectSource(
    selectedSourceId: String?,
    sources: List<SourceModel>,
    onSelectSourceId: (String) -> Unit,
) {
    SelectWithBottomSheet(
        label = "Source",
        selectedLabel = { source: SourceModel -> "${source.id} (Balance: ${source.balance} VNĐ)" },
        items = sources,
        key = { it.id },
        selectedItem = selectedSourceId?.let { id -> sources.find { it.id == id } },
        getItemDescription = { source -> "${source.id} (Balance: ${source.balance} VNĐ)" },
        getItemLongDescription = { source -> "Source ID: ${source.id}, Balance: ${source.balance} VNĐ" },
        onItemSelected = { source -> onSelectSourceId(source.id) },
        modifier = Modifier.padding(bottom = 16.dp)
    )
}