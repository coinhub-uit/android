package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContactMail
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailInputBox(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    isValid: Boolean = false,
    errorMessage: String?,
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
            if (errorMessage != null) Text(errorMessage)
        },
        isError = !isValid,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.ContactMail, contentDescription = "Email"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            autoCorrectEnabled = false,
            imeAction = imeAction,
        ),
        singleLine = true,
    )
}
