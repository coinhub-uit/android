package com.coinhub.android.presentation.transfer_money.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun TransferMoneyReceiptSource(
    receiptSourceId: String,
    onReceiptSourceIdChange: (String) -> Unit,
    receiptUser: UserModel?,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Recipient",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = receiptUser?.fullName ?: "No recipient selected",
            onValueChange = {},
            label = { Text("Recipient's Name") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = receiptSourceId,
            onValueChange = onReceiptSourceIdChange,
            label = { Text("Recipient's Source ID") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview
@Composable
private fun Preview() {
    Surface {
        CoinhubTheme {
            TransferMoneyReceiptSource(
                receiptSourceId = "123456789",
                onReceiptSourceIdChange = {},
                receiptUser = UserModel(
                    id = Uuid.random(),
                    fullName = "Nguyen Van A",
                    citizenId = "123456789",
                    birthDate = LocalDate.now(),
                    avatar = "https://example.com/avatar.png",
                    address = "123 Street, City",
                    createdAt = ZonedDateTime.now(),
                    deletedAt = null
                )
            )
        }
    }
}