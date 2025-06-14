package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SelectWithBottomSheet(
    modifier: Modifier = Modifier,
    label: String,
    selectedLabel: (T) -> String,
    items: List<T>?,
    key: ((T) -> Any)? = null,
    selectedItem: T?,
    getItemDescription: (T) -> String,
    getItemLongDescription: ((T) -> String)? = null,
    onItemSelected: (T) -> Unit,
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    OutlinedCard(
        onClick = { isBottomSheetVisible = true },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedItem?.let { selectedLabel(it) } ?: label
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = label
            )
        }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible = false
            },
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                items?.let {
                    LazyColumn {
                        items(items, key = key) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onItemSelected(item)
                                        isBottomSheetVisible = false
                                    }
                                    .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = getItemDescription(item), style = MaterialTheme.typography.bodyLarge)
                                    if (getItemLongDescription != null) {
                                        Text(
                                            text = getItemLongDescription(item),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                if (selectedItem != null && key?.invoke(selectedItem) == key?.invoke(item)) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = "Selected",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            if (item != items.last()) {
                                HorizontalDivider()
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}