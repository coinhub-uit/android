package com.coinhub.android.presentation.top_up

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
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.data.models.TopUpStatusEnum
import com.coinhub.android.presentation.top_up.components.TopUpResultStatus
import com.coinhub.android.presentation.top_up_result.state.TopUpState
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
    val topUpState = viewModel.topUpState.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val message = viewModel.message.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.checkTopUpStatus(topUpId)
    }

    if (message != null) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    TopUpResultScreen(
        topUpState = topUpState,
        isLoading = isLoading,
        onRetry = {
            //viewModel.checkTopUpStatus(sourceId = topUp.)
        },
        onMain = onMain
    )
}

@Composable
fun TopUpResultScreen(
    topUpState: TopUpState,
    isLoading: Boolean,
    onRetry: () -> Unit,
    onMain: () -> Unit,
) {
    when (topUpState) {
        is TopUpState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Error...")
            }
        }

        TopUpState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Loading...")
            }
        }

        is TopUpState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(64.dp)
                ) {
                    Column {
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

                        TopUpResultStatus(
                            topUpStatus = topUpState.topUpModel.status
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            AnimatedVisibility(
                                visible = topUpState.topUpModel.status != TopUpStatusEnum.success
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
    }
}

@OptIn(ExperimentalUuidApi::class)
@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
private fun PreviewScreen() {
    Surface {
        CoinhubTheme {
            TopUpResultScreen(
                topUpState = TopUpState.Success(
                    TopUpModel(
                        id = Uuid.random(),
                        provider = TopUpProviderEnum.vnpay,
                        amount = BigInteger("1000000"),
                        status = TopUpStatusEnum.success,
                        createdAt = ZonedDateTime.parse("2023-01-01"),
                    )
                ),
                isLoading = false,
                onRetry = {},
                onMain = {}
            )
        }
    }
}
