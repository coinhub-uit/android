package com.coinhub.android.presentation.top_up

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.presentation.top_up.components.TopUpEnterAmount
import com.coinhub.android.presentation.top_up.components.TopUpSelectProvider
import com.coinhub.android.presentation.top_up.components.TopUpSelectSource
import com.coinhub.android.presentation.top_up.components.TopUpTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopUpScreen(
    onBack: () -> Unit,
    viewModel: TopUpViewModel = hiltViewModel(),
) {
    val selectedSourceId = viewModel.selectedSourceId.collectAsStateWithLifecycle().value
    val sources = viewModel.sources.collectAsStateWithLifecycle().value
    val topUpProvider = viewModel.topUpProvider.collectAsStateWithLifecycle().value
    val amount = viewModel.amount.collectAsStateWithLifecycle().value
    val isFormValid = viewModel.isFormValid.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    LaunchedEffect(Unit) {
        viewModel.createTopUp.collect {
            val intent = Intent(Intent.ACTION_VIEW, it.url.toUri())
            context.startActivity(intent)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    with(sharedTransitionScope) {
        TopUpScreen(
            selectedSourceId = selectedSourceId,
            sources = sources,
            selectedProvider = topUpProvider,
            amountText = amount,
            isFormValid = isFormValid,
            onSelectSource = viewModel::selectSource,
            onSelectProvider = viewModel::selectProvider,
            onAmountChange = viewModel::updateAmount,
            onPresetAmountClick = viewModel::setPresetAmount,
            onCreateTopUp = viewModel::createTopUp,
            onBack = onBack,
            modifier = Modifier.sharedBounds(
                animatedVisibilityScope = animatedVisibilityScope, sharedContentState = rememberSharedContentState(
                    key = "topUp",
                )
            ),
        )
    }
}

@Composable
private fun TopUpScreen(
    modifier: Modifier = Modifier,
    selectedSourceId: String?,
    sources: List<SourceModel>,
    selectedProvider: TopUpModel.ProviderEnum?,
    amountText: String,
    isFormValid: Boolean,
    onSelectSource: (String) -> Unit,
    onSelectProvider: (TopUpModel.ProviderEnum) -> Unit,
    onAmountChange: (String) -> Unit,
    onPresetAmountClick: (String) -> Unit,
    onCreateTopUp: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopUpTopBar(
                onBack = onBack,
            )
        }, floatingActionButton = {
            if (isFormValid) {
                FloatingActionButton(
                    onClick = onCreateTopUp,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next"
                    )
                }
            }
        }, modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TopUpSelectSource(
                selectedSourceId = selectedSourceId, sources = sources, onSelectSource = onSelectSource
            )

            Spacer(modifier = Modifier.height(24.dp))

            TopUpSelectProvider(
                selectedProvider = selectedProvider, onSelectProvider = onSelectProvider
            )

            Spacer(modifier = Modifier.height(24.dp))

            TopUpEnterAmount(
                amountText = amountText, onAmountChange = onAmountChange, onPresetAmountClick = onPresetAmountClick
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun TopUpScreenPreview() {
    CoinhubTheme {
        Surface {
            val sources = listOf(
                SourceModel("1", BigInteger("1000000")),
                SourceModel("2", BigInteger("500000")),
                SourceModel("3", BigInteger("750000"))
            )
            TopUpScreen(
                selectedSourceId = "1",
                sources = sources,
                selectedProvider = null,
                amountText = "",
                isFormValid = false,
                onSelectSource = {},
                onSelectProvider = {},
                onAmountChange = {},
                onPresetAmountClick = {},
                onCreateTopUp = {},
                onBack = {})
        }
    }
}
