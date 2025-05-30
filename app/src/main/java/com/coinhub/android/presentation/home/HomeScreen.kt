package com.coinhub.android.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.presentation.home.components.HomeFeatures
import com.coinhub.android.presentation.home.components.HomeGreeting
import com.coinhub.android.presentation.home.components.HomeListSource
import com.coinhub.android.presentation.home.components.HomeTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
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
    // Mock user data
    val sourceModels = listOf(
        SourceModel("01123142213512521", 9999999999999999),
        SourceModel("00", 1200000),
        SourceModel("KevinNitroSourceId", 0),
    )
    // NOTE: Remember to remove OptIn
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
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HomeGreeting(userModel = userModel)
            Spacer(modifier = Modifier.height(32.dp))
            HomeListSource(
                sourceModels = sourceModels,
                navigateToSourceDetail = navigateToSourceDetail,
                copySourceIdToClipboard =copySourceIdToClipboard
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

@PreviewLightDark
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
