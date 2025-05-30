package com.coinhub.android.presentation.navigation.app.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter

@Composable
fun AvatarPicker(avatarUri: Uri?, onAvatarUriChange: (Uri) -> Unit) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        run {
            if (uri != null)
                onAvatarUriChange(uri)
        }
    }


    Image(
        painter = rememberAsyncImagePainter(
            model = avatarUri
        ),
        contentDescription = "Avatar",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .padding(bottom = 8.dp)
            .clickable {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
    )

    Text(
        "Press to choose avatar",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Preview()
@Composable
fun AvatarPickerPreview() {
    AvatarPicker(
        avatarUri = "https://placehold.co/150".toUri(),
        onAvatarUriChange = {}
    )
}