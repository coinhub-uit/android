package com.coinhub.android.presentation.source_detail

import androidx.compose.foundation.layout.Arrangement
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
import java.time.ZonedDateTime

@Composable
fun SourceDetailScreen(
    source: SourceModel,
    onBack: () -> Unit,
    onSourceQr: () -> Unit,
    viewModel: SourceDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    val isProcessing = viewModel.isProcessing.collectAsState().value

    SourceDetailScreen(
        source = source,
        isProcessing = isProcessing,
        onBack = onBack,
        onSourceQr = onSourceQr,
        onCloseSource = {
            viewModel.closeSource(source) {
                onBack()
            }
        },
        copySourceIdToClipboard = {
            viewModel.copySourceIdToClipboard(context, source.id)
        })
}

@Composable
private fun SourceDetailScreen(
    source: SourceModel,
    isProcessing: Boolean,
    onBack: () -> Unit,
    onSourceQr: () -> Unit,
    onCloseSource: () -> Unit,
    copySourceIdToClipboard: () -> Unit,
) {
    Scaffold(
        topBar = {
            SourceDetailTopBar(
                onBack = onBack,
            )
        },
    ) { innerPadding ->
        if (isProcessing) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SourceDetailCard(
                source = source,
                copySourceIdToClipboard = copySourceIdToClipboard,
            )

            Spacer(modifier = Modifier.height(8.dp))

            SourceDetailActions(
                onSourceQr = onSourceQr,
                source = source,
                onCloseSource = onCloseSource,
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SourceDetailScreenPreview() {
    CoinhubTheme {
        SourceDetailScreen(
            source = SourceModel(
                id = "123456789012345",
                balance = BigInteger("5000000"),
                openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
            ),
            isProcessing = false,
            onBack = {},
            onSourceQr = {},
            onCloseSource = {},
            copySourceIdToClipboard = {},
        )
    }
}
