package com.midasmoney.screen.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class ProfileRoute(val route: String) {
    data object Main : ProfileRoute("Profile")
}

@Composable
fun ProfileNavGraph(
    navController: NavHostController,
    shouldShowBottomBar: MutableState<Boolean>,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute.Main.route
    ) {
        composable(route = ProfileRoute.Main.route) {
            shouldShowBottomBar.value = true
            ProfileContentImp(paddingValues)
        }
    }

}