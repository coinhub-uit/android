package com.coinhub.android.presentation.transfer_money.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.presentation.common.components.RowSelectBottomSheet

@Composable
fun TransferMoneySelectSource(
    selectedSourceId: String?,
    sources: List<SourceModel>,
    onSelectSource: (String) -> Unit,
) {
    var isSourceBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    Text(
        text = "Select Source",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    OutlinedTextField(
        value = selectedSourceId?.let { id ->
            sources.find { it.id == id }?.let {
                "${it.id} (Balance: ${it.balance} VNĐ)"
            }
        } ?: "Select Source",
        onValueChange = {},
        readOnly = true,
        label = { Text("Your Source") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isSourceBottomSheetVisible = true
            },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select Source"
            )
        }
    )

    if (isSourceBottomSheetVisible) {
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
                onSelectSource(source.id)
                isSourceBottomSheetVisible = false
            },
            onDismissRequest = { isSourceBottomSheetVisible = false }
        )
    }
}