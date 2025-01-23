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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
@Composable
fun RegisterScreen() {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    /*
    LaunchedEffect(Unit) {
        viewModel.addAllUsers(DataProvider.users)
    }
    */
    viewModel.logout()
    val users = viewModel.usersState.collectAsStateWithLifecycle().value
    val name = remember { mutableStateOf("") }
    val surname = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var message = remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 110.dp, bottom=150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Rejestracja",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 35.dp)
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { newName -> name.value = newName },
                    label = { Text("Imię", fontSize = 29.sp) },
                    modifier = Modifier
                        .width(400.dp)
                        .height(80.dp)
                        .padding(start = 20.dp, end = 20.dp),
                    textStyle = TextStyle(fontSize = 29.sp)
                )
            }
            item {
                OutlinedTextField(
                    value = surname.value,
                    onValueChange = { newSurname -> surname.value = newSurname },
                    label = { Text("Nazwisko", fontSize = 29.sp) },
                    modifier = Modifier
                        .width(400.dp)
                        .height(80.dp)
                        .padding(start = 20.dp, end = 20.dp),
                    textStyle = TextStyle(fontSize = 29.sp)
                )
            }
            item {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { newEmail -> email.value = newEmail },
                    label = { Text("E-mail", fontSize = 29.sp) },
                    modifier = Modifier
                        .width(400.dp)
                        .height(80.dp)
                        .padding(start = 20.dp, end = 20.dp),
                    textStyle = TextStyle(fontSize = 29.sp)
                )
            }
            item {
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
            }
            item {
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
            }
            item {
                Button(
                    onClick = {
                        viewModel.addUser(User(0,name.value.toString(),surname.value.toString(),email.value.toString(),login.value.toString(),password.value.toString(),0))
                        message.value = "Rejestracja przebiegła pomyślnie"
                    },
                    modifier = Modifier
                        .width(400.dp)
                        .height(95.dp)
                        .padding(start = 20.dp, end = 20.dp, top=20.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Zarejestruj się", fontSize = 29.sp)
                }
            }
            item {
                Text(
                    text = message.value,
                    fontSize = 27.sp,
                    color = Color(0xFF37823c),
                    modifier = Modifier.padding(start=10.dp, end=10.dp),
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    GuessTheTimeTheme {
        RegisterScreen()
    }
}