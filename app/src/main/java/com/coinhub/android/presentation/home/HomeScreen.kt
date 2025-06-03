package com.coinhub.android.presentation.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.presentation.home.components.HomeFeatures
import com.coinhub.android.presentation.home.components.HomeGreeting
import com.coinhub.android.presentation.home.components.HomeListSource
import com.coinhub.android.presentation.home.components.HomeTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.math.BigInteger

@Composable
fun HomeScreen(
    onToSourceDetail: () -> Unit,
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onNotification: () -> Unit,
    onAiChat: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // TODO: Replace with real state from viewModel when available
    val sourceModels = listOf(
        SourceModel("01123142213512521", BigInteger("9999999999999999")),
        SourceModel("00", BigInteger("0")),
        SourceModel("KevinNitroSourceId", BigInteger("0")),
    )
    val userModel = viewModel.userModel.collectAsStateWithLifecycle().value
    val copySourceIdToClipboard = viewModel::copySourceIdToClipboard

    HomeScreen(
        userModel = userModel,
        sourceModels = sourceModels,
        onToSourceDetail = onToSourceDetail,
        onTopUp = onTopUp,
        onCreateSource = onCreateSource,
        onTransferMoney = onTransferMoney,
        onNotification = onNotification,
        onAiChat = onAiChat,
        copySourceIdToClipboard = copySourceIdToClipboard,
        modifier = Modifier.padding(bottom = 64.dp)
    )
}

@Composable
private fun HomeScreen(
    userModel: UserModel,
    sourceModels: List<SourceModel>,
    onToSourceDetail: () -> Unit,
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onNotification: () -> Unit,
    onAiChat: () -> Unit,
    copySourceIdToClipboard: (Context, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.padding(16.dp), topBar = {
            HomeTopBar(
                onNotification = onNotification, onAiChat = onAiChat
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            HomeGreeting(userModel = userModel)
            Spacer(modifier = Modifier.height(32.dp))
            HomeListSource(
                sourceModels = sourceModels,
                onToSourceDetail = onToSourceDetail,
                copySourceIdToClipboard = copySourceIdToClipboard
            )
            Spacer(modifier = Modifier.height(16.dp))
            HomeFeatures(
                onTopUp = onTopUp, onCreateSource = onCreateSource, onTransferMoney = onTransferMoney
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun HomeScreenPreview() {
    CoinhubTheme {
        HomeScreen(
            onCreateSource = {},
                   onToSourceDetail = {},
                   onTopUp = {},
                   onNotification = {},
                   onAiChat = {},
                   onTransferMoney = {})
    }
}
