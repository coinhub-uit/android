package com.coinhub.android.presentation.top_up.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.ui.theme.CoinhubTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopUpSelectSource(
    selectedSourceId: String?,
    availableSources: List<SourceModel>,
    isBottomSheetVisible: Boolean,
    onShowBottomSheet: () -> Unit,
    onHideBottomSheet: () -> Unit,
    onSelectSource: (String) -> Unit,
) {
    Text(
        text = "Select Source",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    OutlinedCard(
        onClick = onShowBottomSheet,
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
                    availableSources.find { it.id == id }?.let {
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

    if (isBottomSheetVisible) {
        val bottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = onHideBottomSheet,
            sheetState = bottomSheetState
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Source",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn {
                    items(availableSources) { source ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectSource(source.id) }
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

                        if (source != availableSources.last()) {
                            HorizontalDivider()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TopUpSelectSourcePreview() {
    CoinhubTheme {
        var selectedSourceId by remember { mutableStateOf<String?>(null) }
        var isSheetVisible by remember { mutableStateOf(false) }
        val sources = listOf(
            SourceModel("1", 1000000),
            SourceModel("2", 500000),
            SourceModel("3", 750000)
        )
        Surface {
            TopUpSelectSource(
                selectedSourceId = selectedSourceId,
                availableSources = sources,
                isBottomSheetVisible = isSheetVisible,
                onShowBottomSheet = { isSheetVisible = true },
                onHideBottomSheet = { isSheetVisible = false },
                onSelectSource = {
                    selectedSourceId = it
                    isSheetVisible = false
                }
            )
        }
    }
}
