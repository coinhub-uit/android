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
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.presentation.home.components.HomeFeatures
import com.coinhub.android.presentation.home.components.HomeGreeting
import com.coinhub.android.presentation.home.components.HomeListSource
import com.coinhub.android.presentation.home.components.HomeTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.time.LocalDate
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HomeScreen(
    navigateToSourceDetail: () -> Unit,
    navigateToTopUp: () -> Unit,
    navigateToCreateSource: () -> Unit,
    navigateToTransferMoney: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToAiChat: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // TODO: Replace with real state from viewModel when available
    val sourceModels = listOf(
        SourceModel("01123142213512521", 9999999999999999),
        SourceModel("00", 1200000),
        SourceModel("KevinNitroSourceId", 0),
    )
    val userModel = UserModel(
        id = Uuid.random(),
        birthDate = LocalDate.now(),
        citizenId = "1234567890123",
        createdAt = Date(),
        deletedAt = null,
        avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
        fullName = "NTGNguyen",
        address = null
    )
    val copySourceIdToClipboard = viewModel::copySourceIdToClipboard

    HomeScreen(
        userModel = userModel,
        sourceModels = sourceModels,
        navigateToSourceDetail = navigateToSourceDetail,
        navigateToTopUp = navigateToTopUp,
        navigateToCreateSource = navigateToCreateSource,
        navigateToTransferMoney = navigateToTransferMoney,
        navigateToNotification = navigateToNotification,
        navigateToAiChat = navigateToAiChat,
        copySourceIdToClipboard = copySourceIdToClipboard
    )
}

@Composable
private fun HomeScreen(
    userModel: UserModel,
    sourceModels: List<SourceModel>,
    navigateToSourceDetail: () -> Unit,
    navigateToTopUp: () -> Unit,
    navigateToCreateSource: () -> Unit,
    navigateToTransferMoney: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToAiChat: () -> Unit,
    copySourceIdToClipboard: (Context, String) -> Unit,
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                navigateToNotification = navigateToNotification,
                navigateToAiChat = navigateToAiChat
            )
        }
    ) { innerPadding ->
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
                navigateToSourceDetail = navigateToSourceDetail,
                copySourceIdToClipboard = copySourceIdToClipboard
            )
            Spacer(modifier = Modifier.height(16.dp))
            HomeFeatures(
                navigateToTopUp = navigateToTopUp,
                navigateToCreateSource = navigateToCreateSource,
                navigateToTransferMoney = navigateToTransferMoney
            )
        }
    }
}

@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun HomeScreenPreview() {
    CoinhubTheme {
        HomeScreen(
            navigateToCreateSource = {},
            navigateToSourceDetail = {},
            navigateToTopUp = {},
            navigateToNotification = {},
            navigateToAiChat = {},
            navigateToTransferMoney = {}
        )
    }
}
