package com.coinhub.android.presentation.set_pin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.presentation.set_pin.components.SetPinTopBar
import com.coinhub.android.ui.theme.CoinhubTheme

/**
 * @param onBack Not null mean it's not in edit mode | Change PIN (not called from App Nav)
 */
@Composable
fun SetPinScreen(
    onBack: (() -> Unit)? = null,
    viewModel: SetPinViewModel = hiltViewModel(),
) {
    val pin = viewModel.pin.collectAsStateWithLifecycle().value
    val pinCheckState = viewModel.pinCheckState.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    SetPinScreen(
        onBack = onBack,
        pin = pin,
        onPinChange = viewModel::onPinChange,
        pinCheckState = pinCheckState,
        isFormValid = isFormValid,
        onChangePin = viewModel::onChangePin,
        onCreatePin = viewModel::onCreatePin,
    )
}

@Composable
fun SetPinScreen(
    onBack: (() -> Unit)?,
    pin: String,
    onPinChange: (String) -> Unit,
    pinCheckState: SetPinStates.PinCheckState,
    isFormValid: Boolean,
    onChangePin: () -> Unit,
    onCreatePin: () -> Unit,
) {
    val isEdit = remember(onBack) { onBack != null }

    Scaffold(topBar = {
        SetPinTopBar(onBack = onBack)
    }, floatingActionButton = {
        if (isFormValid) {
            FloatingActionButton(onClick = if (isEdit) onChangePin else onCreatePin) {
                Text(text = if (isEdit) "Save" else "Next")
            }
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Set your 4-digit PIN", modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = pin,
                onValueChange = onPinChange,
                label = { Text("PIN") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done
                ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
                supportingText = {
                    if (!pinCheckState.isValid) {
                        Text(
                            text = pinCheckState.errorMessage ?: "Invalid PIN", color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text("Enter a 4-digit PIN")
                    }
                },
                singleLine = true,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun SetPinScreenPreview() {
    CoinhubTheme {
        SetPinScreen(
            onBack = {},
            pin = "1234",
            onPinChange = {},
            pinCheckState = SetPinStates.PinCheckState(isValid = true, errorMessage = null),
            isFormValid = true,
            onChangePin = {},
            onCreatePin = {},
        )
    }
}
