package com.midasmoney.screen.history.tab

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.midasmoney.screen.history.HistoryViewModel
import com.midasmoney.screen.history.TimeSelector
import com.midasmoney.core.resource.R.string.all
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

sealed class Component(val route: String) {
    data object Filter : Component("filter")
    data object Search : Component("search")
}

data class TabItem(
    val title: String,
    val icon: ImageVector,
    val content: @Composable () -> Unit
)

@Composable
fun FilterComponent(
    viewModel: HistoryViewModel,
    tabs: List<TabItem>,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TabRow(viewModel, tabs)
        TimeSelector(viewModel)
        OutlinedTextField(
            readOnly = true,
            enabled = false,
            value = "",
            onValueChange = { },
            trailingIcon = {
                IconButton(
                    onClick = { navController.navigate(Component.Search.route) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = Icons.Outlined.Search.name
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .clickable {
                    navController.navigate(Component.Search.route)
                }
        )
    }
}

@Composable
fun SearchComponent(viewModel: HistoryViewModel, navController: NavController) {
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.setFilterString(it)
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        navController.navigate(Component.Filter.route) {
                            launchSingleTop = true
                            popUpTo(Component.Filter.route) {
                                inclusive = true
                            }
                        }
                        viewModel.setFilterString(String())
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = Icons.AutoMirrored.Outlined.ArrowBack.name
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .focusRequester(focusRequester)
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
fun TabNavigation(viewModel: HistoryViewModel) {
    val selectedTab by viewModel.selectedTabIndex.collectAsState()
    val tabs = listOf(
        TabItem(stringResource(all), Icons.Default.AllInbox) { Tab(viewModel) },
        TabItem(stringResource(income), Icons.Default.NorthEast) { Tab(viewModel) },
        TabItem(stringResource(expense), Icons.Default.SouthWest) { Tab(viewModel) }
    )
    Column(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Component.Filter.route
        ) {
            composable(Component.Filter.route) {
                FilterComponent(viewModel, tabs, navController)
            }
            composable(Component.Search.route) {
                SearchComponent(viewModel, navController)
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            tabs[selectedTab].content()
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TabNavigationLightPreview() {
    MidasLightPreview {
        val viewModel = HistoryViewModel()
        TabNavigation(viewModel)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TabNavigationDarkPreview() {
    MidasDarkPreview {
        val viewModel = HistoryViewModel()
        TabNavigation(viewModel)
    }
}