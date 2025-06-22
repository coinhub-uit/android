package com.coinhub.android.presentation.top_up.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.common.components.SelectWithBottomSheet
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.CurrencySymbol
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toVndFormat
import java.math.BigInteger
import java.time.ZonedDateTime

@Composable
fun TopUpSelectSource(
    selectedSourceId: String?,
    sources: List<SourceModel>,
    onSelectSource: (String) -> Unit,
) {
    Column {
        Text(
            text = "Select Source",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SelectWithBottomSheet(
            label = "Select Source",
            selectedLabel = { source: SourceModel -> "${source.id} (Balance: ${source.balance.toVndFormat(currencySymbol = CurrencySymbol.VND)})" },
            items = sources,
            key = { it.id },
            selectedItem = sources.find { it.id == selectedSourceId },
            getItemDescription = { "Source ID: ${it.id} (Balance: ${it.balance.toVndFormat(currencySymbol = CurrencySymbol.VND)})" },
            getItemLongDescription = { "ID: ${it.id}" },
            onItemSelected = { onSelectSource(it.id) },
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun TopUpSelectSourcePreview() {
    CoinhubTheme {
        var selectedSourceId by remember { mutableStateOf<String?>("1") }
        val sources = listOf(
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
        )
        Surface {
            TopUpSelectSource(
                selectedSourceId = selectedSourceId, sources = sources, onSelectSource = {
                    selectedSourceId = it
                })
        }
    }
}
