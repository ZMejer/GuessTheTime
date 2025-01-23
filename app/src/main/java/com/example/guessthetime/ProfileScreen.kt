package com.example.guessthetime

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guessthetime.ui.theme.GuessTheTimeTheme

@Composable
fun ProfileScreen() {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState(initial = false)
    val userId = viewModel.userId.collectAsState(initial = 0)
    val user = viewModel.getUserById(userId.value).collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Mój profil",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Imię: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                        append(user.value?.name ?: "")
                    }
                },
                fontSize = 30.sp,
            )
            Text(
                text = buildAnnotatedString {
                    append("Nazwisko: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                        append(user.value?.surname ?: "")
                    }
                },
                fontSize = 30.sp,
            )
            Text(
                text = buildAnnotatedString {
                    append("E-mail: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                        append(user.value?.email ?: "")
                    }
                },
                fontSize = 30.sp,
            )
            Text(
                text = buildAnnotatedString {
                    append("Login: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                        append(user.value?.login ?: "")
                    }
                },
                fontSize = 30.sp,
            )
            Text(
                text = buildAnnotatedString {
                    append("Punkty: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)) {
                        append(user.value?.points.toString() ?: "")
                    }
                },
                fontSize = 30.sp,
                modifier = Modifier.padding(bottom=30.dp)
            )
        }
        Spacer(modifier = Modifier.height(250.dp))
        Button(
            onClick = {
                viewModel.logout()
            },
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Wyloguj się", fontSize = 29.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    GuessTheTimeTheme {
        ProfileScreen()
    }
}