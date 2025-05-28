package com.coinhub.android.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coinhub.android.R

@Composable
fun AuthHeader(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        imageVector = ImageVector.vectorResource(R.drawable.coinhub),
        contentDescription = null,
    )
}

@Preview
@Composable
fun AuthHeaderPreview() {
    AuthHeader()
}
