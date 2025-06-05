package com.coinhub.android.presentation.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.ui.theme.CoinhubTheme
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private val greetings = listOf(
    "Hello", "Hi", "Welcome", "Greetings", "Good day", "Hey there", "Howdy", "Nice to see you", "Welcome back"
)

private fun getRandomGreeting(): String {
    return greetings[Random.nextInt(greetings.size)]
}

@Composable
fun HomeGreeting(userModel: UserModel) {
    val greeting = remember { getRandomGreeting() }

    Text(
        text = "$greeting, ${userModel.fullName}",
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        softWrap = true
    )
}

@OptIn(ExperimentalUuidApi::class)
@PreviewLightDark
@Composable
fun HomeGreetingPreview() {
    val userModel = UserModel(
        id = Uuid.random(),
        birthDate = LocalDate.parse("2000-01-01"),
        citizenId = "1234567890123",
        createdAt = ZonedDateTime.parse("2023-01-01"),
        deletedAt = null,
        avatar = "https://avatars.githubusercontent.com/u/86353526?v=4",
        fullName = "NTGNguyen",
        address = null
    )

    Surface {
        CoinhubTheme {
            HomeGreeting(userModel = userModel)
        }
    }
}
