package com.coinhub.android.presentation.home.components

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.toVndFormat
import java.math.BigInteger

@Composable
fun HomeListSource(
    sourceModels: List<SourceModel>,
    onToSourceDetail: (SourceModel) -> Unit,
    copySourceIdToClipboard: (context: Context, sourceId: String) -> Unit,
) {

    val pagerState = rememberPagerState { sourceModels.size }

    Text(
        text = "Your Sources", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(8.dp))

    HorizontalPager(
        state = pagerState, contentPadding = PaddingValues(end = 32.dp), pageSpacing = 16.dp
    ) { page ->
        HomeSourceCard(
            sourceModel = sourceModels[page],
            onSourceClick = onToSourceDetail,
            copySourceIdToClipboard = copySourceIdToClipboard
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeSourceCard(
    sourceModel: SourceModel,
    onSourceClick: (SourceModel) -> Unit,
    copySourceIdToClipboard: (context: Context, sourceId: String) -> Unit,
) {
    var isBalanceVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "homeSourceCard-${sourceModel.id}",
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                )
                .fillMaxWidth()
                .wrapContentHeight(), colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ), onClick = { onSourceClick(sourceModel) }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .padding(16.dp),
            ) {
                Text(
                    text = sourceModel.id,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = "homeSourceId-${sourceModel.id}",
                            ),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                        .align(Alignment.TopStart)
                        .padding(start = 8.dp, top = 8.dp)
                )

                Text(
                    text = if (isBalanceVisible) sourceModel.balance.toVndFormat() else "****** VNĐ",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 12.dp)
                )

                IconButton(
                    onClick = {
                        copySourceIdToClipboard(context, sourceModel.id)
                        Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
                    }, modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy Source ID",
                    )
                }

                IconButton(
                    onClick = { isBalanceVisible = !isBalanceVisible }, modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = if (isBalanceVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isBalanceVisible) "Hide Balance" else "Show Balance",
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeListSourcePreview() {
    CoinhubTheme {
        HomeListSource(
            sourceModels = listOf(
                SourceModel("01123142213512521", BigInteger("9999999999999999")),
                SourceModel("00", BigInteger("1200000")),
                SourceModel("KevinNitroSourceId", BigInteger("12000000")),
            ), onToSourceDetail = {}, copySourceIdToClipboard = { _, _ -> })
    }
}
