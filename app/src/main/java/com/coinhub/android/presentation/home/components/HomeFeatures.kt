package com.coinhub.android.presentation.home.components

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun HomeFeatures(
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onTransferMoneyQr: () -> Unit,
) {
    val homeFeatureCardItems = listOf(
        HomeFeatureCardItem(
            title = "Top up",
            icon = Icons.Default.Payments,
            onClick = onTopUp,
            shareTransitionKey = "topUp"
        ),
        HomeFeatureCardItem(
            title = "Create source",
            icon = Icons.Default.Add,
            onClick = onCreateSource,
            shareTransitionKey = "createSource"
        ),
        HomeFeatureCardItem(
            title = "Transfer money",
            icon = Icons.Default.SwapHoriz,
            onClick = onTransferMoney,
            shareTransitionKey = "transferMoney"
        ),
        HomeFeatureCardItem(
            title = "Scan",
            icon = Icons.Default.QrCodeScanner,
            onClick = onTransferMoneyQr,
            shareTransitionKey = "transferMoneyQr"
        )
    )

    Column {
        Text(
            text = "Features",
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
            items(homeFeatureCardItems) { item ->
                HomeFeatureCard(
                    title = item.title,
                    icon = item.icon,
                    onClick = item.onClick,
                    modifier = Modifier.fillMaxWidth(),
                    shareTransitionKey = item.shareTransitionKey,
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeFeatureCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    shareTransitionKey: String,
) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")
    with(sharedTransitionScope) {
        Card(
            modifier = modifier
                .sharedBounds(
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedContentState = rememberSharedContentState(
                        key = shareTransitionKey,
                    )
                )
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
}

@Preview
@Composable
fun HomeFeaturesPreview() {
    Surface {
        CoinhubTheme {
            HomeFeatures(
                onTopUp = {},
                onCreateSource = {},
                onTransferMoney = {},
                onTransferMoneyQr = {}
            )
        }
    }
}

private data class HomeFeatureCardItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val shareTransitionKey: String,
)
