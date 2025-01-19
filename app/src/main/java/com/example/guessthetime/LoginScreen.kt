package com.example.guessthetime

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guessthetime.ui.theme.GuessTheTimeTheme
@Composable
fun LoginScreen() {

    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState(initial = false)

    if (isUserLoggedIn.value) {
        ProfileScreen()
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Zaloguj się, aby wyświetlić profil",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                style = TextStyle(lineHeight = 50.sp)
            )
            OutlinedTextField(
                value = login.value,
                onValueChange = { newLogin -> login.value = newLogin },
                label = { Text("Login", fontSize = 29.sp) },
                modifier = Modifier
                    .width(400.dp)
                    .height(80.dp)
                    .padding(start = 20.dp, end = 20.dp),
                textStyle = TextStyle(fontSize = 29.sp)
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = { newPasswd -> password.value = newPasswd },
                label = { Text("Hasło", fontSize = 29.sp) },
                modifier = Modifier
                    .width(400.dp)
                    .height(80.dp)
                    .padding(start = 20.dp, end = 20.dp),
                textStyle = TextStyle(fontSize = 29.sp),
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
                    viewModel.validateLogin(login.value, password.value)
                },
                modifier = Modifier
                    .width(400.dp)
                    .height(85.dp)
                    .padding(start = 20.dp, end = 20.dp, top=10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Zaloguj się", fontSize = 29.sp)
            }
            Text(
                text = if (isUserLoggedIn.value) "Zalogowany: true" else "Zalogowany: false",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    GuessTheTimeTheme {
        LoginScreen()
    }
}