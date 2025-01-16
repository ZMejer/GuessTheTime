package com.example.guessthetime

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guessthetime.ui.theme.GuessTheTimeTheme

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
        viewModel.deleteAllUsers()
    }
    */
    val users = viewModel.usersState.collectAsStateWithLifecycle().value
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(top=40.dp)
    ) {
        items(users.size) {
            Text(
                text = "${users[it].name} ${users[it].surname}",
                fontSize = 29.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .padding(end = 20.dp)
            )
            Text(
                text = "${users[it].login} ${users[it].email}",
                fontSize = 29.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .padding(end = 20.dp)
            )
            Text(
                text = "${users[it].id} ${users[it].password}",
                fontSize = 29.sp,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .padding(end = 20.dp)
            )
        }
    }
    Text(
        text = "Rejestracja",
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    GuessTheTimeTheme {
        RegisterScreen()
    }
}