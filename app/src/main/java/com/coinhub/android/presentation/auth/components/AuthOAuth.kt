package com.coinhub.android.presentation.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.coinhub.android.R

@Composable
fun AuthOAuth(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { /* TODO: Handle Google Sign-In */ }) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.google_svg),
                contentDescription = "Google"
            )
        }
    }
}
