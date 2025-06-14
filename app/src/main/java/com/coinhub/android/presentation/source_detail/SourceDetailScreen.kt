package com.coinhub.android.presentation.source_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.source_detail.components.SourceDetailActions
import com.coinhub.android.presentation.source_detail.components.SourceDetailCard
import com.coinhub.android.presentation.source_detail.components.SourceDetailTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@Composable
fun SourceDetailScreen(
    source: SourceModel,
    onBack: () -> Unit,
    onSourceQr: () -> Unit,
    viewModel: SourceDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isLoading = viewModel.isLoading.collectAsState().value
    val showCloseDialog = viewModel.showCloseDialog.collectAsState().value
    val showBalanceErrorDialog = viewModel.showBalanceErrorDialog.collectAsState().value

    SourceDetailScreen(
        source = source,
        isLoading = isLoading,
        showCloseDialog = showCloseDialog,
        showBalanceErrorDialog = showBalanceErrorDialog,
        onBack = onBack,
        onSourceQr = onSourceQr,
        onCloseClick = { viewModel.onCloseClick(source) },
        onCloseConfirm = viewModel::onCloseConfirm,
        dismissCloseDialog = viewModel::dismissCloseDialog,
        dismissBalanceErrorDialog = viewModel::dismissBalanceErrorDialog,
        copySourceIdToClipboard = {
            viewModel.copySourceIdToClipboard(context, source.id)
        })
}

@Composable
private fun SourceDetailScreen(
    source: SourceModel,
    isLoading: Boolean,
    showCloseDialog: Boolean,
    showBalanceErrorDialog: Boolean,
    onBack: () -> Unit,
    onSourceQr: () -> Unit,
    onCloseClick: () -> Unit,
    onCloseConfirm: () -> Unit,
    dismissCloseDialog: () -> Unit,
    dismissBalanceErrorDialog: () -> Unit,
    copySourceIdToClipboard: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Scaffold(
            topBar = {
                SourceDetailTopBar(
                    onBack = onBack,
                    onClose = onCloseClick,
                    showCloseDialog = showCloseDialog,
                    showBalanceErrorDialog = showBalanceErrorDialog,
                    onCloseConfirm = onCloseConfirm,
                    dismissCloseDialog = dismissCloseDialog,
                    dismissBalanceErrorDialog = dismissBalanceErrorDialog
                )
            }) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SourceDetailCard(
                    source = source, copySourceIdToClipboard = copySourceIdToClipboard
                )

                Spacer(modifier = Modifier.height(8.dp))

                SourceDetailActions(onSourceQr = onSourceQr)
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SourceDetailScreenPreview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel("123456789012345", BigInteger("5000000")),
            isLoading = false,
            showCloseDialog = false,
            showBalanceErrorDialog = false,
            onBack = {},
            onSourceQr = {},
            onCloseClick = {},
            onCloseConfirm = {},
            dismissCloseDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = {},
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun Preview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel("123456789012345", BigInteger.ZERO),
            isLoading = false,
            showCloseDialog = true,
            showBalanceErrorDialog = false,
            onBack = {},
            onSourceQr = {},
            onCloseClick = {},
            onCloseConfirm = {},
            dismissCloseDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = {},
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun ErrorPreview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel("123456789012345", BigInteger("1000000")),
            isLoading = false,
            showCloseDialog = false,
            showBalanceErrorDialog = true,
            onBack = {},
            onSourceQr = {},
            onCloseClick = {},
            onCloseConfirm = {},
            dismissCloseDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = {},
        )
    }
}
