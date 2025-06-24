package com.coinhub.android.presentation.auth.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.coinhub.android.R

@Composable
fun AuthOAuth(modifier: Modifier = Modifier, onClick: () -> Unit, isProcessing: Boolean) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = !isProcessing,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.google_svg),
            contentDescription = "Google"
        )
    }
}
