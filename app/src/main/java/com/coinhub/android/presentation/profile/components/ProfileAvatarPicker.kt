package com.coinhub.android.presentation.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.coinhub.android.presentation.common.components.Avatar

@Composable
fun ProfileAvatarPicker(avatarUri: Uri?, onAvatarUriChange: (Uri) -> Unit) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        run {
            if (uri == null) return@run
            onAvatarUriChange(uri)
        }
    }

    if (avatarUri != null) {
        Avatar(
            modifier = Modifier
                .size(128.dp),
            onClick = {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
            model = avatarUri,
        )
    } else {
        Avatar(
            modifier = Modifier
                .size(128.dp),
            onClick = {
                imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ProfileAvatarPicker(
        avatarUri = "https://placehold.co/150".toUri(),
        onAvatarUriChange = {},
    )
}