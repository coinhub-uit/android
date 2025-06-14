package com.coinhub.android.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun MenuAvatar(
    user: UserModel?,
) {
    val avatarSize = 128.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        if (user?.avatar != null) {
            AvatarImage(
                avatarUrl = user.avatar,
                fullName = user.fullName,
                avatarSize = avatarSize,
            )
        } else {
            InitialAvatar(
                fullName = user?.fullName ?: "...",
                avatarSize = avatarSize,
            )
        }
        Text(
            text = user?.fullName ?: "...",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = true,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun AvatarImage(
    avatarUrl: String,
    fullName: String,
    avatarSize: Dp,
) {
    SubcomposeAsyncImage(
        model = avatarUrl, contentDescription = "Avatar for $fullName", contentScale = ContentScale.Crop, loading = {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }, modifier = Modifier
            .size(avatarSize)
            .clip(CircleShape)
            .border(
                width = 3.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape
            )
    )
}

@Composable
private fun InitialAvatar(
    fullName: String,
    avatarSize: Dp,
) {
    val initial = fullName.first().toString()

    Box(
        modifier = Modifier
            .size(avatarSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onSecondary)
            .border(
                width = 3.dp, color = MaterialTheme.colorScheme.secondary, shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun MenuAvatarPreview() {
    CoinhubTheme {
        MenuAvatar(
            user = UserModel(
                id = Uuid.random(),
                birthDate = LocalDate.parse("2000-01-01"),
                citizenId = "1234567890123",
                createdAt = ZonedDateTime.parse("2023-01-01"),
                deletedAt = null,
                avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
                fullName = "NTGNguyen",
                address = null
            ),
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun MenuAvatarNoImagePreview() {
    CoinhubTheme {
        MenuAvatar(
            user = UserModel(
                id = Uuid.random(),
                birthDate = LocalDate.parse("2000-01-01"),
                citizenId = "1234567890123",
                createdAt = ZonedDateTime.parse("2023-01-01"),
                deletedAt = null,
                avatar = null,
                fullName = "NTGNguyen",
                address = null
            ),
        )
    }
}
