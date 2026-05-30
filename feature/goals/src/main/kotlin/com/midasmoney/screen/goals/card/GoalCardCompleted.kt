package com.midasmoney.screen.goals.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Goal
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

@Composable
fun GoalCardCompleted(goal: Goal) {
    Column(
        modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp),
    ) {
        MidasCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(78.dp),
        ) {
            Row(
                modifier = Modifier.padding(start = 17.dp, top = 19.dp, bottom = 12.dp, end = 17.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.padding(end = 12.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier =
                            Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                .padding(10.dp),
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = goal.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                    Text(
                        text = "${stringResource(R.string.target)}: ${goal.targetDate.format(
                            kotlinx.datetime.LocalDate.Format {
                                monthName(MonthNames.ENGLISH_ABBREVIATED)
                                char(' ')
                                year()
                            },
                        )}",
                        fontSize = 13.sp,
                        color = MidasColors.Gray,
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = goal.amount.toCurrency(),
                        color = MidasColors.Green.primary,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        text = stringResource(R.string.goal_completed),
                        fontSize = 13.sp,
                        color = MidasColors.Gray,
                    )
                }
            }
        }
    }
}

@CustomPreview
@Composable
fun CompletedGoalCardPreview() {
    MidasTheme {
        GoalCardCompleted(goal = Database.goalList.first())
    }
}
