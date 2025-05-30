package com.coinhub.android.presentation.menu.components

import androidx.compose.foundation.background
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
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun MenuAvatar(
    userModel: UserModel,
) {
    val avatarSize = 128.dp
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if (userModel.avatar != null) {
            AvatarImage(
                avatarUrl = userModel.avatar,
                fullName = userModel.fullName,
                avatarSize = avatarSize,
            )
        } else {
            InitialAvatar(
                fullName = userModel.fullName,
                avatarSize = avatarSize,
            )
        }
        Text(
            text = userModel.fullName,
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
    avatarSize: Dp
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
        modifier = Modifier.size(avatarSize)
            .clip(CircleShape)
    )
}

@Composable
private fun InitialAvatar(
    fullName: String,
    avatarSize: Dp
) {
    val initial = fullName.first().toString()

    Box(
        modifier = Modifier.size(avatarSize)
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
fun MenuAvatarPreview() {
    CoinhubTheme {
        MenuAvatar(
            userModel = UserModel(
                id = Uuid.random().toString(),
                birthDate = LocalDate.now().toString(),
                citizenId = "1234567890123",
                createdAt = Date().toString(),
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
            userModel = UserModel(
                id = Uuid.random().toString(),
                birthDate = LocalDate.now().toString(),
                citizenId = "1234567890123",
                createdAt = Date().toString(),
                deletedAt = null,
                avatar = null,
                fullName = "NTGNguyen",
                address = null
            ),
        )
    }
}
