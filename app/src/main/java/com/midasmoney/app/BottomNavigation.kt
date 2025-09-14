package com.midasmoney.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Adjust
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    val items = listOf(
        Screen.Home to Triple(Screen.Home.route, Icons.Outlined.Home, Icons.Filled.Home),
        Screen.History to Triple(
            Screen.History.route,
            Icons.Outlined.History,
            Icons.Filled.History
        ),
        Screen.Goals to Triple(Screen.Goals.route, Icons.Outlined.Adjust, Icons.Filled.Adjust),
        Screen.Profile to Triple(Screen.Profile.route, Icons.Outlined.Person, Icons.Filled.Person)
    )
    NavigationBar {
        items.forEach { (screen, item) ->
            NavigationBarItem(
                icon = { MidasNavigationBarItemIcon(currentRoute, screen, item) },
                label = { Text(item.first) },
                selected = currentRoute == screen.route,
                onClick = onClickNavigationBarItem(currentRoute, screen, navController),
                colors = midasNavigationBarItemColors(),
            )
        }
    }
}

@Composable
private fun MidasNavigationBarItemIcon(
    currentRoute: String?,
    screen: Screen,
    item: Triple<String, ImageVector, ImageVector>
) {
    if (currentRoute == screen.route) {
        Icon(
            painter = rememberVectorPainter(item.third),
            contentDescription = item.first
        )
    } else {
        Icon(
            painter = rememberVectorPainter(item.second),
            contentDescription = item.first
        )
    }
}

@Composable
private fun onClickNavigationBarItem(
    currentRoute: String?,
    screen: Screen,
    navController: NavHostController
): () -> Unit = {
    if (currentRoute != screen.route) {
        navController.navigate(screen.route) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
private fun midasNavigationBarItemColors(
): NavigationBarItemColors {
    return NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.secondary,
        selectedTextColor = MaterialTheme.colorScheme.secondary,
        selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
        disabledIconColor = MaterialTheme.colorScheme.onBackground,
        disabledTextColor = MaterialTheme.colorScheme.onBackground
    )
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController, "Home")
}