package com.coinhub.android.presentation.transfer_money.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.common.components.CurrencyInputBox

@Composable
fun TransferMoneyEnterAmount(
    amount: String,
    onAmountChange: (String) -> Unit,
) {
    Text(
        text = "Transfer Amount",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    CurrencyInputBox(
        value = amount,
        onValueChange = onAmountChange,
        imeAction = ImeAction.Done,
        modifier = Modifier.fillMaxWidth()
    )
}