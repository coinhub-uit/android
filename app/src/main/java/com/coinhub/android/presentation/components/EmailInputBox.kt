package com.coinhub.android.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailInputBox(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Default,
) {
    OutlinedTextField(
        modifier = modifier.semantics { contentType = ContentType.EmailAddress },
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        label = { Text("Email") },
        supportingText = {
            if (isError)
                Row {
                    Text(
                        "Please enter a valid email address.",
                        Modifier.clearAndSetSemantics {})
                    Spacer(Modifier.weight(1f))
                }
        },
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = "profile"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            autoCorrectEnabled = false,
            imeAction = imeAction,
        ),
    )
}
