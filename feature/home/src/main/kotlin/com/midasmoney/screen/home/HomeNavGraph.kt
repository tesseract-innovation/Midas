package com.midasmoney.screen.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.midasmoney.screen.goals.GoalsNavGraph
import com.midasmoney.screen.history.HistoryNavGraph

sealed class HomeRoute(val route: String) {
    data object Main : HomeRoute("Home")
    data object History: HomeRoute("History")
    data object Goals: HomeRoute("Goals")
}

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.Main.route
    ) {
        composable(route = HomeRoute.Main.route) {
            shouldShowBottomBar.value = true
            HomeContentImp(navController, paddingValues)
        }
        composable(route = HomeRoute.History.route) {
            shouldShowBottomBar.value = true
            HistoryNavGraph(rememberNavController(), shouldShowBottomBar, paddingValues)
        }
        composable(route = HomeRoute.Goals.route) {
            shouldShowBottomBar.value = true
            GoalsNavGraph(rememberNavController(), shouldShowBottomBar, paddingValues)
        }
    }
}