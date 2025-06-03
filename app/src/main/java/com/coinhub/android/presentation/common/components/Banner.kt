package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.R
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun Banner(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.Bottom
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.coinhub),
            contentDescription = null,
            modifier = Modifier
                .weight(1f, fill = false)
        )
        Text(
            text = "CoinHub",
            modifier = Modifier.weight(2f, fill = false)
        )
    }
}
@Preview
@Composable
fun BannerPreview() {
    Surface {
        CoinhubTheme {
            Banner()
        }
    }
}