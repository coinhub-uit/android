package com.coinhub.android.presentation.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.presentation.common.components.Avatar
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun MenuAvatar(
    user: UserModel?,
) {
    val modifier = Modifier.size(128.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        if (user?.avatar != null) {
            Avatar(
                modifier = modifier,
                onClick = null,
                model = user.avatar,
                fullName = user.fullName,
            )
        } else if (user != null) {
            Avatar(
                modifier = modifier,
                onClick = null,
                fullName = user.fullName,
            )
        }

        Text(
            text = user?.fullName ?: "...",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun MenuAvatarPreview() {
    CoinhubTheme {
        Surface {
            MenuAvatar(
                user = UserModel(
                    id = Uuid.random(),
                    birthDate = LocalDate.parse("2000-01-01"),
                    citizenId = "1234567890123",
                    createdAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                    deletedAt = null,
                    avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
                    fullName = "NTGNguyen",
                    address = null
                ),
            )
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun MenuAvatarNoImagePreview() {
    CoinhubTheme {
        Surface {
            MenuAvatar(
                user = UserModel(
                    id = Uuid.random(),
                    birthDate = LocalDate.parse("2000-01-01"),
                    citizenId = "1234567890123",
                    createdAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                    deletedAt = null,
                    avatar = null,
                    fullName = "NTGNguyen",
                    address = null
                ),
            )
        }
    }
}
