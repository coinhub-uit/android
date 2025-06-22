package com.coinhub.android.presentation.source_detail.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme
import java.math.BigInteger
import java.time.ZonedDateTime

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SourceDetailActions(
    onSourceQr: () -> Unit,
    source: SourceModel,
    onCloseSource: () -> Unit,
) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    var showCloseDialog by remember {
        mutableStateOf(false)
    }

    val cardItems = listOf(
        CardItem(
            title = "QR",
            icon = Icons.Default.QrCode,
            onClick = onSourceQr,
            transitionKey = { sourceId: String -> "sourceQr-$sourceId" },
        ),
        CardItem(
            title = "Close",
            icon = Icons.Default.Delete,
            onClick = {
                showCloseDialog = true
            },
        )
    )

    Column {
        Text(
            text = "Actions",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.wrapContentSize()
        ) {
            with(sharedTransitionScope) {
                items(cardItems) { item ->
                    FeatureCard(
                        title = item.title,
                        icon = item.icon,
                        onClick = item.onClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .apply {
                                item.transitionKey?.let { key ->
                                    sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            key = key(item.title),
                                        ),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                    )
                                }
                            }
                    )
                }
            }
        }
    }

    if (showCloseDialog) {
        CloseSourceDialog(
            source = source,
            onDismissRequest = { showCloseDialog = false },
            onConfirm = {
                onCloseSource()
                showCloseDialog = false
            }
        )
    }
}

@Composable
private fun CloseSourceDialog(
    source: SourceModel,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (source.balance != BigInteger.ZERO) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text("Cannot Close Source") },
            text = { Text("You cannot close this source while its balance is not zero.") },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text("OK")
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text("Close Source") },
            text = { Text("Are you sure you want to close this source? This action cannot be undone.") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("OK")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismissRequest) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun FeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .wrapContentHeight(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun HomeFeaturesPreview() {
    Surface {
        CoinhubTheme {
            SourceDetailActions(
                onSourceQr = {},
                onCloseSource = {},
                source = SourceModel(
                    id = "1",
                    balance = BigInteger.ZERO,
                    openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                )
            )
        }
    }
}

private data class CardItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val transitionKey: ((String) -> String)? = null,
)
