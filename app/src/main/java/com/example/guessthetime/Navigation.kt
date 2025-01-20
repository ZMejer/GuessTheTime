package com.example.guessthetime

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController)},
        content = { BottomNavGraph(navController = navController) }
    )
}
@Composable
fun BottomNavGraph(navController: NavHostController){
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if(isUserLoggedIn.value) {
            Screens.LoginScreen.route
        } else {
            Screens.LoginScreen.route
        }
    ) {
        composable(route = Screens.RegisterScreen.route){ RegisterScreen() }
        composable(route = Screens.LoginScreen.route){ LoginScreen() }
        composable(route = Screens.GameScreen.route){ GameScreen() }
        composable(route = Screens.LeaderBoardScreen.route){ LeaderBoardScreen() }
    }
}

@Composable
fun BottomMenu(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState(initial = false)
    val screens = if (isUserLoggedIn.value) {
        listOf(
            BottomBar.GameScreen, BottomBar.LeaderBoardScreen, BottomBar.LoginScreen
        )
    } else {
        listOf(
            BottomBar.RegisterScreen, BottomBar.LoginScreen
        )
    }


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        modifier = Modifier.height(150.dp)
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.title, fontSize = 20.sp)
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = "icon",
                        modifier = Modifier.size(40.dp)
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) }
            )
        }
    }
}