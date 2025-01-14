package com.example.guessthetime

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.guessthetime.ui.theme.GuessTheTimeTheme

@Composable
fun RegisterScreen() {
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