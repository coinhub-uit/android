package com.coinhub.android.presentation.top_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.common.components.CurrencyInputBox
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

private val DefaultPresetAmounts = listOf(
    "50.000", "100.000", "200.000", "500.000", "1.000.000", "2.000.000"
)

@Composable
fun TopUpEnterAmount(
    amountText: String,
    onAmountChange: (String) -> Unit,
    presetAmounts: List<String> = DefaultPresetAmounts,
    onPresetAmountClick: (String) -> Unit
) {
    Text(
        text = "Enter Amount",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    CurrencyInputBox(
        value = amountText,
        onValueChange = onAmountChange,
        imeAction = ImeAction.Done,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        text = "Quick Amount Selection",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(160.dp)
    ) {
        items(presetAmounts) { amount ->
            ElevatedCard(
                onClick = { onPresetAmountClick(amount) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = amount,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(widthDp = PreviewDeviceSpecs.WIDTH)
@Composable
private fun TopUpEnterAmountPreview() {
    CoinhubTheme {
        var amount by remember { mutableStateOf("") }
        Surface {
            TopUpEnterAmount(
                amountText = amount,
                onAmountChange = { amount = it },
                onPresetAmountClick = { amount = it.replace(".", "") }
            )
        }
    }
}
