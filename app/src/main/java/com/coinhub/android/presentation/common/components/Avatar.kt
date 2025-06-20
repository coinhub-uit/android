package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
    model: Any,
    fullName: String? = null,
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = if (fullName != null) "Avatar for $fullName" else null,
        contentScale = ContentScale.Crop,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        },
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape
            )
            .let {
                if (onClick != null) {
                    it.clickable { onClick() }
                } else {
                    it
                }
            }
    )
}

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)?,
    fullName: String,
) {
    val initial = fullName.first().toString()

    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSecondary)
            .border(
                width = 3.dp, color = MaterialTheme.colorScheme.secondary, shape = CircleShape
            )
            .let {
                if (onClick != null) {
                    it.clickable { onClick() }
                } else {
                    it
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        val fontSize = (minOf(maxWidth, maxHeight).value * 0.5f).sp

        Text(
            text = initial,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            maxLines = 1
        )
    }
}

@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSecondary)
            .border(
                width = 3.dp, color = MaterialTheme.colorScheme.secondary, shape = CircleShape
            )
            .let {
                if (onClick != null) {
                    it.clickable { onClick() }
                } else {
                    it
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Empty Avatar",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size((minOf(maxWidth, maxHeight).value * 0.6f).dp)
        )
    }
}
