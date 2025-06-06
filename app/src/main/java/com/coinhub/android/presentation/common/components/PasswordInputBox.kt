package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PasswordInputBox(
    modifier: Modifier = Modifier,
    password: String = "",
    onPasswordChange: (String) -> Unit = {},
    isValid: Boolean = false,
    errorMessage: String?,
    label: String = "Password",
    showPassword: Boolean,
    setShowPassword: (Boolean) -> Unit,
    contentType: ContentType = ContentType.Password,
    imeAction: ImeAction = ImeAction.Default,
) {
    OutlinedTextField(
        modifier = modifier.semantics {
            this.contentType = contentType
        },
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = {
            Text(label)
        },
        supportingText = {
            if (errorMessage != null) Text(errorMessage)
        },
        isError = !isValid,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = "profile"
            )
        },
        trailingIcon = {
            val iconImage =
                if (showPassword) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (showPassword) "Show Password" else "Hide Password"
            IconButton(onClick = {
                setShowPassword(!showPassword)
            }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrectEnabled = false,
            imeAction = imeAction,
        ),
        singleLine = true,
    )
}
