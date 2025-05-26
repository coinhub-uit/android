package com.coinhub.android


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.coinhub.android.presentation.navigation.NavGraph
import com.coinhub.android.ui.theme.CoinhubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        NavGraph()
                    }
                }
            }
        }
    }
}

//@OptIn(AuthUiExperimental::class)
//@Composable
//fun MainScreen(viewModel: SupabaseViewModel = viewModel()) {
//    val context = LocalContext.current
//    val userState by viewModel.userState
//    var userEmail by remember { mutableStateOf("") }
//    var userPassword by remember { mutableStateOf("") }
//
//    var currentUserState by remember { mutableStateOf("") }
//
//    val action = SupabaseClient.client.composeAuth.rememberSignInWithGoogle(
//        onResult = { result -> viewModel.checkGoogleLoginStatus(context, result) },
//        fallback = {}
//    )
//
//    LaunchedEffect(Unit) {
//        viewModel.isUserSignedIn(context)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(8.dp)
//    ) {
//        TextField(
//            value = userEmail,
//            placeholder = {
//                Text(text = "Enter Email")
//            },
//            onValueChange = {
//                userEmail = it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        TextField(
//            value = userPassword,
//            placeholder = {
//                Text(text = "Enter Password")
//            },
//            onValueChange = {
//                userPassword = it
//            }
//        )
//        Spacer(modifier = Modifier.padding(8.dp))
//        Button(onClick = {
//            viewModel.signUp(context, userEmail, userPassword)
//        }) {
//            Text(text = "Sign up")
//        }
//        Button(onClick = {
//            viewModel.signIn(
//                context, userEmail, userPassword
//            )
//        }) {
//            Text(text = "Sign in")
//        }
//        Button(onClick = {
//            viewModel.signOut(context)
//        }) {
//            Text(text = "Sign out")
//        }
//        OutlinedButton(onClick = {
//            action.startFlow()
//        }, content = { ProviderButtonContent(provider = Google) })
//        when (userState) {
//            is UserState.Loading -> {
//                LoadingComponent()
//            }
//
//            is UserState.Success -> {
//                currentUserState = (userState as UserState.Success).message
//            }
//
//            is UserState.Error -> {
//                currentUserState = (userState as UserState.Error).message
//            }
//        }
//        Text(text = currentUserState)
//    }
//}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CoinhubTheme {
//        Greeting("Android")
//    }
//}
//
//
//@Composable
//fun LoadingComponent() {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text("Loading...")
//    }
//}
