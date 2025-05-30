package com.coinhub.android.presentation.top_up.components

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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun TopUpSelectSource(
    selectedSourceId: String?,
    sourceModels: List<SourceModel>,
    isBottomSheetVisible: Boolean,
    setShowBottomSheet: (Boolean) -> Unit,
    onSelectSource: (String) -> Unit,
) {
    Text(
        text = "Select Source",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    OutlinedCard(
        onClick = { setShowBottomSheet(true) },
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

    if (isBottomSheetVisible) {
        val bottomSheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { setShowBottomSheet(false) },
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
                                    onSelectSource(source.id)
                                    setShowBottomSheet(false)
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
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
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
                sourceModels = sources,
                isBottomSheetVisible = isSheetVisible,
                setShowBottomSheet = { isSheetVisible = it },
                onSelectSource = {
                    selectedSourceId = it
                }
            )
        }
    }
}
