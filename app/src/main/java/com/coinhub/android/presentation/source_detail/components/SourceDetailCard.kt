package com.coinhub.android.presentation.source_detail.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.utils.CurrencySymbol
import com.coinhub.android.utils.PreviewDeviceSpecs
import com.coinhub.android.utils.toVndFormat
import java.time.ZonedDateTime

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SourceDetailCard(
    source: SourceModel,
    copySourceIdToClipboard: () -> Unit,
) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "homeSourceCard-${source.id}",
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxWidth()
                .wrapContentHeight(), colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = source.id,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "homeSourceId-${source.id}",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = source.balance.toVndFormat(currencySymbol = CurrencySymbol.VND),
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    key = "homeSourceBalance-${source.id}",
                                ),
                                animatedVisibilityScope = animatedVisibilityScope,
                            )
                    )
                }
                IconButton(
                    onClick = copySourceIdToClipboard, modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Source ID",
                    )
                }
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun SourceDetailCardPreview() {
    SourceDetailCard(
        source = SourceModel(
            id = "source_1234567890",
            balance = 1000000.toBigInteger(),
            openedAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
        ),
        copySourceIdToClipboard = {},
    )
}

