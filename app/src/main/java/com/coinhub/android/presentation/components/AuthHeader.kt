package com.coinhub.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.coinhub.android.R

@Composable
fun AuthHeader(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.coinhub),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun AuthHeaderPreview() {
    AuthHeader()
}
