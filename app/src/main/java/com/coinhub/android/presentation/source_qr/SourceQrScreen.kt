package com.coinhub.android.presentation.source_qr

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.source_qr.components.SourceQrTopBar
import com.coinhub.android.presentation.source_qr.utils.sourceQrGenerate
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun SourceQrScreen(
    sourceId: String,
    onBack: () -> Unit,
) {
    SourceQrScreen(
        sourceId = sourceId,
        onBack = onBack,
        modifier = Modifier // Avoid calling itself :v
    )
}

@Composable
fun SourceQrScreen(
    sourceId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SourceQrTopBar(onBack = onBack)
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier.padding(innerPadding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BoxWithConstraints {
                    val widthPx = constraints.maxWidth
                    val sourceQrBitmap = remember {
                        sourceQrGenerate(
                            sourceId, width = widthPx, height = widthPx
                        )
                    }

                    sourceQrBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Source ID: $sourceId",
                        )
                    }
                }

                Text(
                    text = "Source ID: $sourceId",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        CoinhubTheme {
            SourceQrScreen(sourceId = "1234567890", onBack = {})
        }
    }
}