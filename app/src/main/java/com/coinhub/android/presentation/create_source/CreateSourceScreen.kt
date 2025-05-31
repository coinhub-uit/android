package com.coinhub.android.presentation.create_source

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.create_source.components.CreateSourceTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@Composable
fun CreateSourceScreen(
    onBack: () -> Unit,
    viewModel: CreateSourceViewModel = hiltViewModel(),
) {
    val sourceId = viewModel.sourceId.collectAsState().value
    val sourceCheckState = viewModel.isSourceIdValid.collectAsState().value
    val setSourceId = viewModel::setSourceId
    val isFormValid = viewModel.isFormValid.collectAsState().value

    CreateSourceScreen(
        sourceId = sourceId,
        onSourceIdChange = setSourceId,
        sourceCheckState = sourceCheckState,
        isFormValid = isFormValid,
        onBack = onBack,
        onCreate = viewModel::createSource
    )
}

@Composable
private fun CreateSourceScreen(
    sourceId: String,
    onSourceIdChange: (String) -> Unit,
    sourceCheckState: CreateSourceStates.SourceCheckState,
    isFormValid: Boolean,
    onBack: () -> Unit,
    onCreate: () -> Unit
) {
    Scaffold(
        topBar = {
            CreateSourceTopBar(onBack = onBack)
        },
        floatingActionButton = {
            if (isFormValid) {
                ExtendedFloatingActionButton(
                    onClick = onCreate,
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create Source"
                        )
                    },
                    text = { Text("Create") }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Please enter your Source ID",
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = sourceId,
                onValueChange = onSourceIdChange,
                label = { Text("Source ID") },
                isError = !sourceCheckState.isValid,
                supportingText = {
                    if (!sourceCheckState.isValid && sourceCheckState.errorMessage != null) {
                        Text(sourceCheckState.errorMessage)
                    }
                },
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun CreateSourceScreenPreview() {
    CoinhubTheme {
        CreateSourceScreen(
            sourceId = "1234",
            onSourceIdChange = {},
            sourceCheckState = CreateSourceStates.SourceCheckState(
                isValid = false,
                errorMessage = "NO"
            ),
            isFormValid = true,
            onBack = {},
            onCreate = {}
        )
    }
}
