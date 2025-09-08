package com.midasmoney.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.midasmoney.shared.ui.core.color.MidasTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidasTheme(isTrueBlack = true) {
                Midas()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Midas() {
    val navController = rememberNavController()
    val shouldShowBottomBar = remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigationManagerView(shouldShowBottomBar, navController)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowBottomBar.value) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) {
        SetupNavGraph(
            navController = navController,
            shouldShowBottomBar = shouldShowBottomBar
        )
    }
}

@Composable
fun BottomNavigationManagerView(
    shouldShowBottomBar: MutableState<Boolean>,
    navController: NavHostController
) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collectLatest { backStackEntry ->
            backStackEntry.destination.route?.let { route ->
                Log.d("BottomNavigationManagerView", "Current route: $route")
                shouldShowBottomBar.value = when (route) {
                    Screen.Home.route -> true
                    Screen.Analytics.route -> true
                    Screen.History.route -> true
                    Screen.Goals.route -> true
                    Screen.Profile.route -> true
                    else -> false
                }
                Log.d("BottomNavigationManagerView", "ShouldShowBottomBar: $shouldShowBottomBar")
            }
        }
    }
}