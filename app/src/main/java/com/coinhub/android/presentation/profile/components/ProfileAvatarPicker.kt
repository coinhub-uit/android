package com.coinhub.android.presentation.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.coinhub.android.presentation.common.components.Avatar

@Composable
fun ProfileAvatarPicker(avatarUri: Uri?, onAvatarUriChange: (Uri?) -> Unit, isEdit : Boolean) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        run {
            if (uri == null) return@run
            onAvatarUriChange(uri)
        }
    }

    Box(contentAlignment = Alignment.Center) {
        if (avatarUri != null) {
            Avatar(
                modifier = Modifier
                    .size(128.dp),
                onClick = {
                    imagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                model = avatarUri,
            )

            // Add remove button if in edit mode and avatar exists
            if (isEdit) {
                FilledIconButton(
                    onClick = { onAvatarUriChange(null) },
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = 48.dp, y = (-48).dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "Remove avatar",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
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
}

@Preview
@Composable
private fun Preview() {
    ProfileAvatarPicker(
        avatarUri = "https://placehold.co/150".toUri(),
        onAvatarUriChange = {},
        isEdit = true
    )
}