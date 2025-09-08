package com.midasmoney.screen.goals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class GoalsRoute(val route: String) {
    data object Main : GoalsRoute("Goals")
}

@Composable
fun GoalsNavGraph(navController: NavHostController, shouldShowBottomBar: MutableState<Boolean>) {
    NavHost(
        navController = navController,
        startDestination = GoalsRoute.Main.route
    ) {
        composable(route = GoalsRoute.Main.route) {
            shouldShowBottomBar.value = true
            GoalsContentImp()
        }
    }
}