package com.example.guessthetime

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LeaderBoardScreen() {

    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val leaderboard = viewModel.leaderboard.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Ranking",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(bottom = 160.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(leaderboard.size) { index ->
                LeaderBoardItem(leaderboard[index],index, leaderboard.size)
            }
        }
    }
}

@Composable
fun LeaderBoardItem(user: User, idx: Int, numElemets: Int) {
    Log.d("LeaderBoardItem", "User: ${user.name}, Points: ${user.points}")
    var pdg = 5
    if (idx == 0) {
        pdg = 15
    }
    var pdg_bot = 0
    if (idx==numElemets-1) {
        pdg_bot = 15
    }
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(start = 30.dp, end = 30.dp, top = pdg.dp, bottom = pdg_bot.dp)
            .height(50.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${idx + 1}. ${user.name}", fontSize = 20.sp)
            Text(text = user.points.toString(), fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderBoardPreview() {
    LeaderBoardScreen()
}