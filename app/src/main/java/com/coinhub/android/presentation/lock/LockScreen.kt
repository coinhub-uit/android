package com.coinhub.android.presentation.lock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.common.components.Banner
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@Composable
fun LockScreen(
    viewModel: LockViewModel = hiltViewModel(),
) {
    val pin by viewModel.pin.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val context = LocalContext.current

    LockScreen(
        pin = pin,
        onPinChange = viewModel::setPin,
        isLoading = isLoading,
        onUnlock = viewModel::unlock,
        onBiometricClick = viewModel.getBiometricPromptCallback(context)
    )
}

@Composable
private fun LockScreen(
    pin: String,
    onPinChange: (String) -> Unit,
    isLoading: Boolean,
    onUnlock: () -> Unit,
    onBiometricClick: (() -> Unit)?,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Banner(modifier = Modifier.padding(bottom = 24.dp))

            Text(
                text = "Enter your PIN to unlock", modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pin,
                onValueChange = onPinChange,
                label = { Text("PIN") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    if (onBiometricClick != null) {
                        IconButton(onClick = onBiometricClick) {
                            Icon(
                                imageVector = Icons.Default.Fingerprint,
                                contentDescription = "Use biometric authentication"
                            )
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onUnlock, enabled = pin.length == 4 && !isLoading, modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .align(Alignment.CenterVertically), strokeWidth = 2.dp
                    )
                }
                Text("Unlock")
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun LockScreenPreview() {
    CoinhubTheme {
        Surface {
            LockScreen(
                pin = "123",
                onPinChange = {},
                isLoading = false,
                onUnlock = {},
                onBiometricClick = {},
            )
        }
    }
}
