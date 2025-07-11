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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.shared.ui.core.MidasPreview
import com.midasmoney.shared.ui.core.Theme

@Composable
fun HomeContentImp() {
    HomeContent()
}

@Composable
fun HomeContent(isDarkTheme: Boolean = isSystemInDarkTheme()) {
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
                    totalValue = "$14,250.00",
                    incomeValue = "+$3,200",
                    expenseValue = "-$1,850",
                    isDarkTheme = isDarkTheme
                )
                TitleItem(
                    textTitle = "Recent Transactions",
                    textButton = "View All",
                    actionButton = {}
                )
                TransactionCard(
                    transactionHistoryItem = Database.transactionHistoryList[0],
                    isDarkTheme = isDarkTheme
                )
                TransactionCard(
                    transactionHistoryItem = Database.transactionHistoryList[1],
                    isDarkTheme = isDarkTheme
                )
                TransactionCard(
                    transactionHistoryItem = Database.transactionHistoryList[2],
                    isDarkTheme = isDarkTheme
                )
                TransactionCard(
                    transactionHistoryItem = Database.transactionHistoryList[3],
                    isDarkTheme = isDarkTheme
                )
                TransactionCard(
                    transactionHistoryItem = Database.transactionHistoryList[4],
                    isDarkTheme = isDarkTheme
                )
                TitleItem(
                    textTitle = "Financial Goals",
                    textButton = "Manage",
                    actionButton = {}
                )
                GoalCard(
                    goal = Database.goalList[0],
                    isDarkTheme = isDarkTheme
                )
                GoalCard(
                    goal = Database.goalList[1],
                    isDarkTheme = isDarkTheme
                )
                GoalCard(
                    goal = Database.goalList[2],
                    isDarkTheme = isDarkTheme
                )
                GoalCard(
                    goal = Database.goalList[3],
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        HomeContent()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        HomeContent(isDarkTheme = true)
    }
}