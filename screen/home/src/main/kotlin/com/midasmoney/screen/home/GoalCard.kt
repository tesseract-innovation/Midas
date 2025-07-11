package com.midasmoney.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.shared.ui.core.MidasColors
import com.midasmoney.shared.ui.core.MidasPreview
import com.midasmoney.shared.ui.core.Theme

@Composable
fun GoalCard(
    goal: Goal,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            isDarkTheme = isDarkTheme
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 18.dp),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.2f)
                        ) {
                            Icon(
                                imageVector = goal.icon,
                                contentDescription = goal.description,
                                tint = goal.color,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(goal.color.copy(alpha = 0.3f))
                                    .padding(10.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .weight(0.6f)
                        ) {
                            Row {
                                Text(
                                    text = goal.title,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Row {
                                Text(
                                    text = "$${goal.progress} / $${goal.amount}",
                                    fontSize = 15.sp,
                                    color = if (isDarkTheme) MidasColors.Gray else MidasColors.Gray,
                                    fontWeight = FontWeight.W400
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .weight(0.3f)
                        ) {
                            Text(
                                text = "${(goal.progress/goal.amount)*100}%",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = {(goal.progress/goal.amount).toFloat()},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = goal.color,
                            trackColor = if(isDarkTheme) MidasColors.DarkGray else MidasColors.ExtraLightGray
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun GoalsCardLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        GoalCard(Database.goalList[0])
    }
}

@Preview
@Composable
fun GoalsCardDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        GoalCard(goal = Database.goalList[0], isDarkTheme = true)
    }
}