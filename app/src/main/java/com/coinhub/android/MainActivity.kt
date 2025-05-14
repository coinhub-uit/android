package com.coinhub.android


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coinhub.android.authentication.SupabaseViewModel
import com.coinhub.android.authentication.data.model.UserState
import com.coinhub.android.authentication.data.network.SupabaseClient
import com.coinhub.android.ui.theme.CoinhubTheme
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.compose.auth.ui.ProviderButtonContent
import io.github.jan.supabase.compose.auth.ui.annotations.AuthUiExperimental
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinhubTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@OptIn(AuthUiExperimental::class)
@Composable
fun MainScreen(viewModel: SupabaseViewModel = viewModel()) {
    val context = LocalContext.current
    val userState by viewModel.userState
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    var currentUserState by remember { mutableStateOf("") }

    val action = SupabaseClient.client.composeAuth.rememberSignInWithGoogle(
        onResult = { result -> viewModel.checkGoogleLoginStatus(context, result) },
        fallback = {}
    )

    LaunchedEffect(Unit) {
        viewModel.isUserSignedIn(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TextField(
            value = userEmail,
            placeholder = {
                Text(text = "Enter Email")
            },
            onValueChange = {
                userEmail = it
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = userPassword,
            placeholder = {
                Text(text = "Enter Email")
            },
            onValueChange = {
                userPassword = it
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {
            viewModel.signUp(context, userEmail, userPassword)
        }) {
            Text(text = "Sign up")
        }
        Button(onClick = {
            viewModel.signIn(
                context, userEmail, userPassword
            )
        }) {
            Text(text = "Sign in")
        }
        Button(onClick = {
            viewModel.signOut(context)
        }) {
            Text(text = "Sign out")
        }
        OutlinedButton(onClick = {
            action.startFlow()
        }, content = { ProviderButtonContent(provider = Google) })
        when (userState) {
            is UserState.Loading -> {
                LoadingComponent()
            }

            is UserState.Success -> {
                currentUserState = (userState as UserState.Success).message
            }

            is UserState.Error -> {
                currentUserState = (userState as UserState.Error).message
            }
        }
        Text(text = currentUserState)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoinhubTheme {
        Greeting("Android")
    }
}


@Composable
fun LoadingComponent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Loading...")
    }
}
