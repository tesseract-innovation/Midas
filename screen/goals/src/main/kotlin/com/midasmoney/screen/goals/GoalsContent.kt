package com.midasmoney.screen.goals

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
import com.midasmoney.screen.goals.card.BalanceGoalStatusCard
import com.midasmoney.screen.goals.card.CompletedGoalCard
import com.midasmoney.screen.goals.card.GoalCard
import com.midasmoney.screen.goals.card.MonthlyInsights
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun GoalsContentImp() {
    GoalsContent()
}

@Composable
fun GoalsContent(
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                BalanceGoalStatusCard(
                    balance = Database.balance,
                )
                TitleItem(
                    textTitle = "Active Goals",
                    textButton = "View All",
                    actionButton = {}
                )
                Database.goalList.forEach {
                    GoalCard(it)
                }
                TitleItem(
                    textTitle = "Recently Completed",
                    textButton = "View All",
                    actionButton = {}
                )
                CompletedGoalCard()
                CompletedGoalCard()
                CompletedGoalCard()
                MonthlyInsights(
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsContentLightPreview() {
    MidasLightPreview {
        GoalsContent()
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsContentDarkPreview() {
    MidasDarkPreview {
        GoalsContent(true)
    }
}