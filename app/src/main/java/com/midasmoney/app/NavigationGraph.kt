package com.midasmoney.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.midasmoney.screen.analytics.AnalyticsNavGraph
import com.midasmoney.screen.goals.GoalsNavGraph
import com.midasmoney.screen.history.HistoryNavGraph
import com.midasmoney.screen.home.HomeNavGraph
import com.midasmoney.screen.profile.ProfileNavGraph

sealed class Screen(val route: String) {
    data object Home : Screen("Home")
    data object History : Screen("History")
    data object Goals : Screen("Goals")
    data object Profile : Screen("Profile")
}

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeNavGraph(
                rememberNavController(),
                shouldShowBottomBar
            )
        }
        composable(Screen.History.route) {
            HistoryNavGraph(
                rememberNavController(),
                shouldShowBottomBar
            )
        }
        composable(Screen.Goals.route) {
            GoalsNavGraph(
                rememberNavController(),
                shouldShowBottomBar
            )
        }
        composable(Screen.Profile.route) {
            ProfileNavGraph(
                rememberNavController(),
                shouldShowBottomBar
            )
        }
    }
}