package com.coinhub.android.presentation.top_up_result

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.data.models.TopUpStatusEnum
import com.coinhub.android.presentation.navigation.AppNavDestinations
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@Composable
fun TopUpResultScreen(
    onMain: () -> Unit,
    topUp: AppNavDestinations.TopUpResult,
    viewModel: TopUpResultViewModel = hiltViewModel(),
) {
    val topUpModel = viewModel.topUpModel.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val message = viewModel.message.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.checkTopUpStatus(sourceId = topUp.sourceId)
    }

    if (message != null) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading && topUpModel == null) {
                // Show loading indicator when initially loading
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(32.dp))
                }
            } else {
                // If we have a model or finished loading, show the details
                topUpModel?.let { model ->
                    TopUpResultScreen(
                        onMain = onMain,
                        provider = model.provider,
                        amount = model.amount.toLong(),
                        topUpStatus = model.status,
                        isLoading = isLoading,
                        checkTopUpStatus = { viewModel.checkTopUpStatus(topUp.sourceId) }
                    )
                } ?: run {
                    // Error state - no data after loading
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Could not load top-up details",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.checkTopUpStatus(topUp.sourceId) }) {
                            Text("Retry")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onMain) {
                            Text("Return to Main")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopUpResultScreen(
    onMain: () -> Unit,
    provider: TopUpProviderEnum,
    amount: Long,
    topUpStatus: TopUpStatusEnum,
    isLoading: Boolean,
    checkTopUpStatus: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Top-Up Details",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Provider: ${provider.name}", style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Amount: $amount", style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (topUpStatus) {
                    TopUpStatusEnum.PROCESSING -> {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Processing",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFFFFA000) // Amber
                        )
                        Text(
                            text = "Processing",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFFFFA000)
                        )
                        Text(
                            text = "Your top-up is being processed. Please wait.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    TopUpStatusEnum.SUCCESS -> {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF4CAF50) // Green
                        )
                        Text(
                            text = "Success",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF4CAF50)
                        )
                        Text(
                            text = "Your top-up was completed successfully!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    TopUpStatusEnum.DECLINED -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Declined",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFFF44336) // Red
                        )
                        Text(
                            text = "Declined",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFFF44336)
                        )
                        Text(
                            text = "Your top-up was declined. Please try again.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    TopUpStatusEnum.OVERDUE -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Overdue",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF9E9E9E) // Gray
                        )
                        Text(
                            text = "Overdue",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF9E9E9E)
                        )
                        Text(
                            text = "Your top-up request has expired.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = checkTopUpStatus, modifier = Modifier.fillMaxWidth(), enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Check Status",
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Check Status")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onMain, modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Return to Main")
                }
            }
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun TopUpResultScreenPreview() {
    CoinhubTheme {
        TopUpResultScreen(
            onMain = {},
            provider = TopUpProviderEnum.MOMO,
            amount = 500000,
            topUpStatus = TopUpStatusEnum.SUCCESS,
            isLoading = false,
            checkTopUpStatus = {}
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE, name = "Processing State")
@Composable
fun TopUpResultScreenProcessingPreview() {
    CoinhubTheme {
        TopUpResultScreen(
            onMain = {},
            provider = TopUpProviderEnum.VNPAY,
            amount = 200000,
            topUpStatus = TopUpStatusEnum.PROCESSING,
            isLoading = false,
            checkTopUpStatus = {}
        )
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE, name = "Declined State")
@Composable
fun TopUpResultScreenDeclinedPreview() {
    CoinhubTheme {
        TopUpResultScreen(
            onMain = {},
            provider = TopUpProviderEnum.ZALOPAY,
            amount = 100000,
            topUpStatus = TopUpStatusEnum.DECLINED,
            isLoading = false,
            checkTopUpStatus = {}
        )
    }
}
