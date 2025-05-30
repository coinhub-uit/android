package com.coinhub.android.presentation.home.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun HomeListSource(
    sourceModels: List<SourceModel>,
    navigateToSourceDetail: () -> Unit,
    copySourceIdToClipboard: (context: Context, sourceId: String) -> Unit,
) {

    val pagerState = rememberPagerState { sourceModels.size }

    Text(
        text = "Your Sources",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(8.dp))

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 32.dp),
        pageSpacing = 16.dp
    ) { page ->
        HomeSourceCard(
            sourceModel = sourceModels[page],
            onSourceClick = navigateToSourceDetail,
            copySourceIdToClipboard = copySourceIdToClipboard
        )
    }
}

@Composable
private fun HomeSourceCard(
    sourceModel: SourceModel,
    onSourceClick: () -> Unit,
    copySourceIdToClipboard: (context: Context, sourceId: String) -> Unit,
) {
    var isBalanceVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        onClick = onSourceClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(end = 64.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = sourceModel.id,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = "${if (isBalanceVisible) sourceModel.balance else "******"} VNĐ",
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = {
                    copySourceIdToClipboard(context, sourceModel.id)
                    Toast.makeText(context, "Source ID copied", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy Source ID",
                )
            }

            IconButton(
                onClick = { isBalanceVisible = !isBalanceVisible },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isBalanceVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isBalanceVisible) "Hide Balance" else "Show Balance",
                )
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
                SourceModel("01123142213512521", 9999999999999999),
                SourceModel("00", 1200000),
                SourceModel("KevinNitroSourceId", 0),
            ),
            navigateToSourceDetail = {},
            copySourceIdToClipboard = { _, _ -> }
        )
    }
}
