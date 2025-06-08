package com.coinhub.android.presentation.top_up_result

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.presentation.top_up_result.components.TopUpResultStatus
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun TopUpResultScreen(
    topUpId: String?,
    onMain: () -> Unit,
    viewModel: TopUpResultViewModel = hiltViewModel(),
) {
    val topUp = viewModel.topUp.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(topUpId) {
        if (topUpId != null) {
            viewModel.checkTopUpStatus(topUpId)
        }
    }

    TopUpResultScreen(
        isLoading = isLoading,
        topUp = topUp,
        onRetry = {
            viewModel.checkTopUpStatus(topUpId)
        },
        onMain = onMain
    )
}

@Composable
fun TopUpResultScreen(
    isLoading: Boolean,
    topUp: TopUpModel?,
    onRetry: () -> Unit,
    onMain: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ),
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Top Up Result",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                TopUpResultStatus(topUp = topUp)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AnimatedVisibility(
                        visible = topUp != null && topUp.status != TopUpModel.StatusEnum.success
                    ) {
                        Button(onClick = onRetry, modifier = Modifier.weight(1f)) {
                            Text("Retry")
                        }
                    }
                    Button(
                        onClick = onMain,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Go to Home")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun PreviewScreen() {
    Surface {
        CoinhubTheme {
            TopUpResultScreen(
                isLoading = false,
                onRetry = {},
                onMain = {},
                topUp = TopUpModel(
                    id = Uuid.random(),
                    provider = TopUpModel.ProviderEnum.vnpay,
                    amount = BigInteger("1000000"),
                    status = TopUpModel.StatusEnum.success,
                    createdAt = ZonedDateTime.now()
                )
            )
        }
    }
}
