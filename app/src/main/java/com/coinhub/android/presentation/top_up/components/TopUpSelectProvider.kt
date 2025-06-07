package com.coinhub.android.presentation.top_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.ui.theme.CoinhubTheme

@Composable
fun TopUpSelectProvider(
    selectedProvider: TopUpModel.ProviderEnum?,
    onSelectProvider: (TopUpModel.ProviderEnum) -> Unit,
) {
    Column {
        Text(
            text = "Select Provider",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(TopUpModel.ProviderEnum.entries.toTypedArray()) { provider ->
                FilterChip(
                    selected = selectedProvider == provider,
                    onClick = { onSelectProvider(provider) },
                    label = { Text(provider.name) }
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun TopUpSelectProviderPreview() {
    CoinhubTheme {
        var selectedProvider by remember { mutableStateOf<TopUpModel.ProviderEnum?>(null) }
        Surface {
            TopUpSelectProvider(
                selectedProvider = selectedProvider,
                onSelectProvider = { selectedProvider = it }
            )
        }
    }
}
