package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.coinhub.android.R
import com.coinhub.android.ui.theme.CoinhubTheme
import androidx.compose.ui.Alignment

@Composable
fun Banner(
    modifier: Modifier = Modifier,
    imageSize: Dp = 40.dp, // Default size, can be overridden
) {
    // Scale text size relative to image size (e.g., 60% of image height)
    val textSize = (imageSize.value * 0.6).sp

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.coinhub),
            contentDescription = null,
            modifier = Modifier.size(imageSize)
        )
        Text(
            text = "CoinHub",
            fontSize = textSize
        )
    }
}

@Preview
@Composable
fun BannerPreview() {
    Surface {
        CoinhubTheme {
            Banner(imageSize = 40.dp)
        }
    }
}
