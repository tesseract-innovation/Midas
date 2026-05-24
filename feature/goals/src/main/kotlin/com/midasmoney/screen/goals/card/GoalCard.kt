package com.midasmoney.screen.goals.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R.string.monthly
import com.midasmoney.core.resource.R.string.of
import com.midasmoney.core.resource.R.string.target
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.theme.MidasTheme
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char


@Composable
fun GoalCard(
    goal: Goal,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    onCardClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onAddMoneyClick: () -> Unit = {},
) {
    val icon = goal.icon.let {
        IconConverter.getImageVector(it)
    }
    val color = goal.color.let {
        ColorConverter.aRgbToColor(it)
    }
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clickable { onCardClick() }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 18.dp, top = 12.dp, bottom = 12.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(end = 12.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = goal.description,
                                tint = color,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(color.copy(alpha = 0.2f))
                                    .padding(10.dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.Start,
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
                                    text = "${stringResource(target)}: " + goal.targetDate.format(
                                        kotlinx.datetime.LocalDate.Format {
                                            monthName(MonthNames.ENGLISH_ABBREVIATED)
                                            char(' ')
                                            dayOfMonth()
                                        }
                                    ),

                                    fontSize = 15.sp,
                                    color = MidasColors.Gray,
                                    fontWeight = FontWeight.W400
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        Text(
                            text = "${goal.progress.toCurrency()} ${stringResource(of)} ${goal.amount.toCurrency()}",
                            fontSize = 15.sp,
                            color = MidasColors.Gray,
                            fontWeight = FontWeight.W400
                        )
                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = "${(goal.progress / goal.amount) * 100}%",
                                fontWeight = FontWeight.Bold,
                                color = color
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp)
                    ) {
                        LinearProgressIndicator(
                            progress = { (goal.progress / goal.amount).toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(CircleShape),
                            color = color,
                            trackColor = if (isDarkTheme) MidasColors.DarkGray else MidasColors.ExtraLightGray
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${stringResource(monthly)}: ${goal.monthlyValue.toCurrency()}",
                            fontSize = 15.sp,
                            color = MidasColors.Gray,
                            fontWeight = FontWeight.W400
                        )
                    }
                }
            }
        }
    }
}

@CustomPreview
@Composable
fun GoalsCardPreview() {
    MidasTheme {
        GoalCard(
            goal = Database.goalList.first(),
        )
    }
}
