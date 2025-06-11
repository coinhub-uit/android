package com.coinhub.android.presentation.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.presentation.home.components.HomeFeatures
import com.coinhub.android.presentation.home.components.HomeGreeting
import com.coinhub.android.presentation.home.components.HomeListSource
import com.coinhub.android.presentation.home.components.HomeTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs

@OptIn(ExperimentalMaterial3Api::class)
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
    val sources = viewModel.sources.collectAsStateWithLifecycle().value
    val user = viewModel.user.collectAsStateWithLifecycle().value
    val copySourceIdToClipboard = viewModel::copySourceIdToClipboard
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isRefreshing = viewModel.isRefreshing.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = viewModel::refresh,
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreen(
            user = user,
            sources = sources,
            onToSourceDetail = onToSourceDetail,
            onTopUp = onTopUp,
            onCreateSource = onCreateSource,
            onTransferMoney = onTransferMoney,
            onTransferMoneyQr = onTransferMoneyQr,
            onNotification = onNotification,
            onAiChat = onAiChat,
            copySourceIdToClipboard = copySourceIdToClipboard,
            isLoading = isLoading,
            modifier = Modifier.padding(bottom = 64.dp) // Trick
        )
    }
}

@Composable
private fun HomeScreen(
    user: UserModel?,
    sources: List<SourceModel>,
    onToSourceDetail: (SourceModel) -> Unit,
    onTopUp: () -> Unit,
    onCreateSource: () -> Unit,
    onTransferMoney: () -> Unit,
    onTransferMoneyQr: () -> Unit,
    onNotification: () -> Unit,
    onAiChat: () -> Unit,
    copySourceIdToClipboard: (Context, String) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier, topBar = {
            HomeTopBar(
                onNotification = onNotification, onAiChat = onAiChat
            )
        }) { innerPadding ->
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            HomeGreeting(user = user)
            Spacer(modifier = Modifier.height(32.dp))
            HomeListSource(
                sources = sources,
                onToSourceDetail = onToSourceDetail,
                copySourceIdToClipboard = copySourceIdToClipboard
            )
            HomeFeatures(
                onTopUp = onTopUp,
                onCreateSource = onCreateSource,
                onTransferMoney = onTransferMoney,
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
