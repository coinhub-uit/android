package com.coinhub.android.presentation.menu

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.presentation.menu.components.MenuAvatar
import com.coinhub.android.presentation.menu.components.MenuItem
import com.coinhub.android.presentation.menu.components.MenuTopBar
import com.coinhub.android.ui.theme.CoinhubTheme
import com.coinhub.android.utils.PreviewDeviceSpecs
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun MenuScreen(
    onEditProfile: () -> Unit,
    onSettings: () -> Unit,
    onChangePin: () -> Unit,
    onCredentialChange: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val user = viewModel.user.collectAsStateWithLifecycle().value
    val sources = viewModel.sources.collectAsStateWithLifecycle().value
    val tickets = viewModel.ticket.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value
    val isRefreshing = viewModel.isRefreshing.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch()
    }

    MenuScreen(
        onSettings = onSettings,
        onChangePin = onChangePin,
        onEditProfile = onEditProfile,
        onCredentialChange = onCredentialChange,
        onDeleteAccount = viewModel::onDeleteAccount,
        onSignOut = viewModel::onSignOut,
        user = user,
        sources = sources,
        tickets = tickets,
        isLoading = isLoading,
        refresh = viewModel::refresh,
        isRefreshing = isRefreshing,
        modifier = Modifier.padding(bottom = 64.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuScreen(
    onSettings: () -> Unit,
    onChangePin: () -> Unit,
    onEditProfile: () -> Unit,
    onDeleteAccount: () -> Unit,
    onSignOut: () -> Unit,
    onCredentialChange: () -> Unit,
    user: UserModel?,
    sources: List<SourceModel>,
    tickets: List<TicketModel>,
    isLoading: Boolean,
    isRefreshing: Boolean,
    refresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val showDeleteAccountDialog = remember {
        mutableStateOf(false)
    }

    val menuSections = mapOf(
        "App" to listOf(
            MenuEntry(
                icon = Icons.Default.Settings,
                text = "Settings",
                onClick = onSettings
            ),
            MenuEntry(
                icon = Icons.Default.Pin,
                text = "Change PIN",
                onClick = onChangePin
            ),
        ),
        "Account" to listOf(
            MenuEntry(
                icon = Icons.Default.Edit,
                text = "Edit Profile",
                onClick = onEditProfile
            ),
            MenuEntry(
                icon = Icons.Default.Password,
                text = "Credentials",
                onClick = onCredentialChange
            ),
        ),
        "Danger" to listOf(
            MenuEntry(
                icon = Icons.Default.Delete,
                text = "Delete Account",
                onClick = {
                    showDeleteAccountDialog.value = true
                }
            ),
        )
    )
    Scaffold(
        topBar = { MenuTopBar(onSignOut = onSignOut) },
        modifier = modifier,
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = refresh,
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                MenuAvatar(user = user)

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp))
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    menuSections.forEach { (_, items) ->
                        item {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                                ),
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    items.forEach { item ->
                                        MenuItem(
                                            icon = item.icon,
                                            text = item.text,
                                            onClick = item.onClick
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDeleteAccountDialog.value) {
            if (sources.isNotEmpty() || tickets.isNotEmpty()) {
                AlertDialog(
                    onDismissRequest = { showDeleteAccountDialog.value = false },
                    title = { Text("Error") },
                    text = { Text("You must close all your sources and withdraw all tickets to delete your account!") },
                    confirmButton = {
                        Button(
                            onClick = { showDeleteAccountDialog.value = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            } else {
                AlertDialog(
                    onDismissRequest = { showDeleteAccountDialog.value = false },
                    title = { Text("Delete Account") },
                    text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showDeleteAccountDialog.value = false
                                onDeleteAccount()
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { showDeleteAccountDialog.value = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

private data class MenuEntry(
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalUuidApi::class)
@Preview(device = PreviewDeviceSpecs.DEVICE)
@Composable
fun MenuScreenPreview() {
    CoinhubTheme {
        MenuScreen(
            onEditProfile = {},
            onSettings = {},
            onChangePin = {},
            onCredentialChange = {},
            onSignOut = {},
            onDeleteAccount = {},
            user = UserModel(
                id = Uuid.NIL,
                birthDate = LocalDate.parse("2000-01-01"),
                citizenId = "1234567890123",
                createdAt = ZonedDateTime.parse("2023-01-01T00:00:00Z"),
                deletedAt = null,
                avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
                fullName = "NTGNguyen",
                address = null
            ),
            sources = emptyList(),
            tickets = emptyList(),
            isLoading = false,
            refresh = {},
            isRefreshing = false,
        )
    }
}