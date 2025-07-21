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
import com.midasmoney.shared.model.mock.Database
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview
import com.midasmoney.shared.resource.R.string.recent_transactions
import com.midasmoney.shared.resource.R.string.view_all
import com.midasmoney.shared.resource.R.string.financial_goals
import com.midasmoney.shared.resource.R.string.manage

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
                    balance = Database.balance,
                    isDarkTheme = isDarkTheme
                )
                TitleItem(
                    textTitle = stringResource(recent_transactions),
                    textButton = stringResource(view_all),
                    actionButton = {}
                )
                for (i in 0 until 2) {
                    TransactionCard(
                        transactionHistoryItem = Database.transactionHistoryList[i],
                    )
                }
                TitleItem(
                    textTitle = stringResource(financial_goals),
                    textButton = stringResource(manage),
                    actionButton = {}
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
    MidasLightPreview {
        HomeContent()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentImpDarkPreview() {
    MidasDarkPreview {
        HomeContent(isDarkTheme = true)
    }
}