package com.midasmoney.screen.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.midasmoney.shared.model.mock.Database
import com.midasmoney.shared.resource.R.string.financial_goals
import com.midasmoney.shared.resource.R.string.manage
import com.midasmoney.shared.resource.R.string.recent_transactions
import com.midasmoney.shared.resource.R.string.view_all
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview
import com.midasmoney.shared.ui.core.component.MidasTitleItem

@Composable
fun HomeContentImp(navController: NavHostController) {
    HomeContent(navController)
}

@Composable
fun HomeContent(navController: NavController, isDarkTheme: Boolean = isSystemInDarkTheme()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                BalanceStatus(
                    balance = Database.balance,
                    isDarkTheme = isDarkTheme
                )
                MidasTitleItem(
                    textTitle = stringResource(recent_transactions),
                    textButton = stringResource(view_all),
                    actionButton = { navController.navigate(HomeRoute.History.route) }
                )
                for (i in 0 until 2) {
                    TransactionCard(
                        transaction = Database.transactions[i],
                    )
                }
                MidasTitleItem(
                    textTitle = stringResource(financial_goals),
                    textButton = stringResource(manage),
                    actionButton = { navController.navigate(HomeRoute.Goals.route) }
                )
                for (i in 0 until 3) {
                    GoalCard(
                        goal = Database.goalList[i]
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpLightPreview() {
    val navController = rememberNavController()
    MidasLightPreview {
        HomeContent(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpDarkPreview() {
    val navController = rememberNavController()
    MidasDarkPreview {
        HomeContent(
            navController = navController,
            isDarkTheme = true
        )
    }
}