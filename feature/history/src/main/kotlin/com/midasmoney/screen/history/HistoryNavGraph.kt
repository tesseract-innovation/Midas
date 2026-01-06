package com.midasmoney.screen.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class HistoryRoute(val route: String) {
    data object Main : HistoryRoute("History")
}

@Composable
fun HistoryNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = HistoryRoute.Main.route
    ) {
        composable(route = HistoryRoute.Main.route) {
            shouldShowBottomBar.value = true
            HistoryContentImp(paddingValues)
        }
    }
}