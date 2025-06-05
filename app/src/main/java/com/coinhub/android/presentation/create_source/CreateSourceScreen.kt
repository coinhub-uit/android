package com.coinhub.android.presentation.create_source

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.presentation.create_source.components.CreateSourceTopBar
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CreateSourceScreen(
    onBack: () -> Unit,
    viewModel: CreateSourceViewModel = hiltViewModel(),
) {
    val sourceId = viewModel.sourceId.collectAsState().value
    val sourceCheckState = viewModel.isSourceIdValid.collectAsState().value
    val setSourceId = viewModel::setSourceId
    val isFormValid = viewModel.isFormValid.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    with(sharedTransitionScope) {
        CreateSourceScreen(
            sourceId = sourceId,
            onSourceIdChange = setSourceId,
            sourceCheckState = sourceCheckState,
            isFormValid = isFormValid,
            isLoading = isLoading,
            onBack = onBack,
            onCreate = { viewModel.createSource(onBack) },
            modifier = Modifier.sharedBounds(
                animatedVisibilityScope = animatedVisibilityScope,
                sharedContentState = rememberSharedContentState(
                    key = "createSource",
                )
            ),
        )
    }
}

@Composable
private fun CreateSourceScreen(
    modifier: Modifier = Modifier,
    sourceId: String,
    onSourceIdChange: (String) -> Unit,
    sourceCheckState: CreateSourceStates.SourceCheckState,
    isFormValid: Boolean,
    isLoading: Boolean,
    onBack: () -> Unit,
    onCreate: () -> Unit,
) {
    Scaffold(
        topBar = {
            CreateSourceTopBar(onBack = onBack)
        }, floatingActionButton = {
            AnimatedVisibility(
                visible = isFormValid && !isLoading,
            ) {
                ExtendedFloatingActionButton(onClick = onCreate, icon = {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = "Create Source"
                    )
                }, text = { Text("Create") })
            }
        }, modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Please enter your Source ID", modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = sourceId,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = onSourceIdChange,
                    label = { Text("Source ID") },
                    isError = !sourceCheckState.isValid,
                    supportingText = {
                        if (!sourceCheckState.isValid && sourceCheckState.errorMessage != null) {
                            Text(sourceCheckState.errorMessage)
                        }
                    },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
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
                isValid = false, errorMessage = "NO"
            ),
            isLoading = false,
            isFormValid = true,
            onBack = {},
            onCreate = {})
    }
}
