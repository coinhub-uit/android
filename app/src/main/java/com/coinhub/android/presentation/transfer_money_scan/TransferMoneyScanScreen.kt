package com.coinhub.android.presentation.transfer_money_scan

import android.app.Activity
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.coinhub.android.presentation.common.permission_requests.RequestCameraPermissionDialog
import com.coinhub.android.presentation.navigation.app.LocalAnimatedVisibilityScope
import com.coinhub.android.presentation.navigation.app.LocalSharedTransitionScope
import com.coinhub.android.presentation.transfer_money_scan.components.TransferMoneyScanTopBar
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TransferMoneyScanScreen(
    onScanned: (String) -> Unit,
    onBack: () -> Unit,
) {
    RequestCameraPermissionDialog()

    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: error("SharedTransitionScope not provided via CompositionLocal")
    val animatedVisibilityScope =
        LocalAnimatedVisibilityScope.current ?: error("AnimatedVisibilityScope not provided via CompositionLocal")

    with(sharedTransitionScope) {
        TransferMoneyScanScreen(
            modifier = Modifier
                .fillMaxSize()
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = "transferMoneyQr",
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                ), onScanned = onScanned, onBack = onBack
        )
    }
}

@Composable
fun TransferMoneyScanScreen(
    modifier: Modifier = Modifier,
    onScanned: (String) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TransferMoneyScanTopBar(onBack = onBack)
        },
        modifier = modifier
    ) { innerPadding ->
        var captureManager: CaptureManager? = null

        AndroidView(
            factory = { context ->
                val preview = CompoundBarcodeView(context).apply {
                    setStatusText("Transfer Money QR Scanner")
                }

                val activity = context as Activity
                captureManager = CaptureManager(activity, preview).apply {
                    initializeFromIntent(activity.intent, null)
                    decode()
                }

                preview.decodeContinuous { result ->
                    result.text?.let { onScanned(it) }
                }

                preview.resume()

                preview
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        )

        DisposableEffect(Unit) {
            onDispose {
                captureManager?.onPause()
                captureManager?.onDestroy()
                captureManager = null
            }
        }
    }
}