package com.coinhub.android.presentation.lock

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LockScreen(
        pin = pin,
        onPinChange = viewModel::setPin,
        onUnlock = viewModel::tryPinUnlock,
        onBiometricClick = viewModel.getBiometricPromptCallback(context)
    )
}

@Composable
private fun LockScreen(
    pin: String,
    onPinChange: (String) -> Unit,
    onUnlock: () -> Unit,
    onBiometricClick: (() -> Unit)?,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Banner(
                modifier = Modifier.padding(top = 128.dp, bottom = 24.dp),
                imageSize = 64.dp
            )

            Text(
                text = "Enter your PIN to unlock", modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pin,
                onValueChange = onPinChange,
                label = { Text("PIN") },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center
                ),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = onUnlock, modifier = Modifier.fillMaxWidth()
            ) {
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
                onUnlock = {},
                onBiometricClick = {},
            )
        }
    }
}
