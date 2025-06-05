package com.coinhub.android.presentation.home

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HomeScreen(
    onToSourceDetail: (SourceModel) -> Unit,
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onTransferMoneyQr: () -> Unit,
    onNotification: () -> Unit,
    onAiChat: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.getUserSources()
        viewModel.getUserOnUserLogin()
    }

    val sourceModels = viewModel.sourceModels.collectAsStateWithLifecycle().value
    val userModel = viewModel.userModel.collectAsStateWithLifecycle().value
    val copySourceIdToClipboard = viewModel::copySourceIdToClipboard

    HomeScreen(
        userModel = userModel ?: UserModel(
            id = Uuid.parse("00000000-0000-0000-0000-000000000000"),
            fullName = "No One",
            citizenId = "0000000000000",
            birthDate = LocalDate.now(),
            createdAt = ZonedDateTime.now(),
            deletedAt = null,
            avatar = null,
            address = null,
        ),
        sourceModels = sourceModels,
        onToSourceDetail = onToSourceDetail,
        onTopUp = onTopUp,
        onCreateSource = onCreateSource,
        onTransferMoney = onTransferMoney,
        onTransferMoneyQr = onTransferMoneyQr,
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
    onToSourceDetail: (SourceModel) -> Unit,
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onTransferMoneyQr: () -> Unit,
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
                onTopUp = onTopUp, onCreateSource = onCreateSource, onTransferMoney = onTransferMoney,
                onTransferMoneyQr = onTransferMoneyQr
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
            onTransferMoney = {},
            onTransferMoneyQr = {},
        )
    }
}
