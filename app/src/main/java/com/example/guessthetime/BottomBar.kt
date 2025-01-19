package com.example.guessthetime
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object RegisterScreen : BottomBar(Screens.RegisterScreen.route, "Rejestracja", Icons.Default.Info)
    data object LoginScreen : BottomBar(Screens.LoginScreen.route, "Mój profil", Icons.Default.Person)
    data object GameScreen : BottomBar(Screens.GameScreen.route, "Rozgrywka", Icons.Default.Home)
    data object LeaderBoardScreen : BottomBar(Screens.LeaderBoardScreen.route, "Ranking", Icons.Default.Star)

}