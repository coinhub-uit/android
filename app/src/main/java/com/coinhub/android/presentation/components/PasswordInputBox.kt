package com.coinhub.android.presentation.components

import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    validatePassword: (String) -> Unit = {},
    isError: Boolean = false,
    supportingText: String = "",
    label: String = "Password",
    contentType: ContentType = ContentType.Password,
    imeAction: ImeAction = ImeAction.Default,
) {
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier.semantics {
            this.contentType = contentType
        },
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        label = {
            Text(if (isError) "${label}*" else label)
        },
        supportingText = {
            Text(supportingText)
        },
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = "profile"
            )
        },
        trailingIcon = {
            val iconImage =
                if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
            val description = if (showPassword) "Hide Password" else "Show Password"
            IconButton(onClick = {
                showPassword = !showPassword
            }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            autoCorrectEnabled = false,
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                validatePassword(password)
            })
    )
}
