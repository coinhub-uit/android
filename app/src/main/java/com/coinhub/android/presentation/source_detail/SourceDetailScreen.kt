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
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.presentation.source_detail.components.SourceDetailCard
import com.coinhub.android.presentation.source_detail.components.SourceDetailActions
import com.coinhub.android.presentation.source_detail.components.SourceDetailTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@Composable
fun SourceDetailScreen(
    source: SourceModel,
    onBack: () -> Unit,
    viewModel: SourceDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isLoading = viewModel.isLoading.collectAsState().value
    val showDeleteDialog = viewModel.showDeleteDialog.collectAsState().value
    val showBalanceErrorDialog = viewModel.showBalanceErrorDialog.collectAsState().value

    SourceDetailScreen(
        source = source,
        isLoading = isLoading,
        showDeleteDialog = showDeleteDialog,
        showBalanceErrorDialog = showBalanceErrorDialog,
        onBack = onBack,
        onDeleteClick = { viewModel.onDeleteClick(source) },
        onDeleteConfirm = viewModel::onDeleteConfirm,
        dismissDeleteDialog = viewModel::dismissDeleteDialog,
        dismissBalanceErrorDialog = viewModel::dismissBalanceErrorDialog,
        copySourceIdToClipboard = {
            viewModel.copySourceIdToClipboard(context, source.id)
        })
}

@Composable
private fun SourceDetailScreen(
    source: SourceModel?,
    isLoading: Boolean,
    showDeleteDialog: Boolean,
    showBalanceErrorDialog: Boolean,
    onBack: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteConfirm: () -> Unit,
    dismissDeleteDialog: () -> Unit,
    dismissBalanceErrorDialog: () -> Unit,
    copySourceIdToClipboard: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        source?.let { sourceModel ->
            Scaffold(
                topBar = {
                    SourceDetailTopBar(
                        onBack = onBack,
                        onDelete = onDeleteClick,
                        showDeleteDialog = showDeleteDialog,
                        showBalanceErrorDialog = showBalanceErrorDialog,
                        onDeleteConfirm = onDeleteConfirm,
                        dismissDeleteDialog = dismissDeleteDialog,
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
                        sourceModel = sourceModel, copySourceIdToClipboard = copySourceIdToClipboard
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SourceDetailActions(onQr = {})
                }
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
            showDeleteDialog = false,
            showBalanceErrorDialog = false,
            onBack = {},
            onDeleteClick = {},
            onDeleteConfirm = {},
            dismissDeleteDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = {})
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SourceDetailScreenDeleteDialogPreview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel("123456789012345", BigInteger.ZERO),
            isLoading = false,
            showDeleteDialog = true,
            showBalanceErrorDialog = false,
            onBack = {},
            onDeleteClick = {},
            onDeleteConfirm = {},
            dismissDeleteDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = { })
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SourceDetailScreenBalanceErrorDialogPreview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel("123456789012345", BigInteger("1000000")),
            isLoading = false,
            showDeleteDialog = false,
            showBalanceErrorDialog = true,
            onBack = {},
            onDeleteClick = {},
            onDeleteConfirm = {},
            dismissDeleteDialog = {},
            dismissBalanceErrorDialog = {},
            copySourceIdToClipboard = {})
    }
}
