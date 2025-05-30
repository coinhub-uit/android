package com.coinhub.android.presentation.top_up.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.ui.theme.CoinhubTheme

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

    OutlinedTextField(
        value = amountText,
        onValueChange = onAmountChange,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        suffix = { Text("VNĐ") },
        singleLine = true,
        placeholder = { Text("0") }
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
                        text = "${amount}Đ",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@PreviewLightDark
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
