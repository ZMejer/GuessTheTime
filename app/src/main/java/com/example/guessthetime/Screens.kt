package com.example.guessthetime

sealed class Screens(val route: String) {
    data object RegisterScreen : Screens("register_screen")
    data object LoginScreen : Screens("login_screen")
}