package com.coinhub.android.presentation.create_ticket.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.common.components.CurrencyInputBox
import com.coinhub.android.utils.toVndFormat

@Composable
fun CreateTicketInputMoney(
    minimumAmount: Long,
    amount: String,
    amountError: String?,
    onAmountChange: (String) -> Unit,
) {
    Column {
        // Input Money Section
        Text(
            text = "Input Money",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Minimum amount: ${minimumAmount.toVndFormat()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CurrencyInputBox(
            value = amount,
            onValueChange = onAmountChange,
            supportingText = {
                if (amountError != null) {
                    Text(
                        text = amountError,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            imeAction = ImeAction.Done,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}