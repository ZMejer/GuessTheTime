package com.example.guessthetime
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object RegisterScreen : BottomBar(Screens.RegisterScreen.route, "Rejestracja", Icons.Default.Home)
    data object LoginScreen : BottomBar(Screens.LoginScreen.route, "Logowanie", Icons.Default.Email)
}