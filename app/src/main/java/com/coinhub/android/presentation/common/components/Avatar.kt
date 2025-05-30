package com.coinhub.android.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun HomeAvatar(
    userModel: UserModel,
    modifier: Modifier,
) {
    if (userModel.avatar != null) {
        AvatarImage(
            avatarUrl = userModel.avatar,
            fullName = userModel.fullName,
            modifier = modifier
        )
    } else {
        InitialAvatar(
            fullName = userModel.fullName,
            modifier = modifier
        )
    }
}

@Composable
private fun AvatarImage(
    avatarUrl: String,
    fullName: String,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        model = avatarUrl,
        contentDescription = "Avatar for $fullName",
        contentScale = ContentScale.Crop,
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
            .clip(CircleShape)
    )
}

@Composable
private fun InitialAvatar(
    fullName: String,
    modifier: Modifier = Modifier,
) {
    val initial = fullName.first().toString()

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
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
fun HomeAvatarPreview() {
    CoinhubTheme {
        // User with avatar
        HomeAvatar(
            userModel = UserModel(
                id = Uuid.random(),
                birthDate = LocalDate.now(),
                citizenId = "1234567890123",
                createdAt = Date(),
                deletedAt = null,
                avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
                fullName = "NTGNguyen",
                address = null
            ),
            modifier = Modifier.size(32.dp)
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun HomeAvatarNoImagePreview() {
    CoinhubTheme {
        // User without avatar
        HomeAvatar(
            userModel = UserModel(
                id = Uuid.random(),
                birthDate = LocalDate.now(),
                citizenId = "1234567890123",
                createdAt = Date(),
                deletedAt = null,
                avatar = null,
                fullName = "NTGNguyen",
                address = null
            ),
            modifier = Modifier.size(32.dp)
        )
    }
}
