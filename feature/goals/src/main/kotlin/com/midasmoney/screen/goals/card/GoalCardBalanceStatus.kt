package com.midasmoney.screen.goals.card

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.domain.model.GoalsSummary
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.resource.R.string.goals_active_count
import com.midasmoney.core.resource.R.string.total_saved
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.preview.MidasLightPreview
import com.midasmoney.core.ui.theme.MidasColors

@Composable
fun GoalCardBalanceStatus(summary: GoalsSummary) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    brush =
                        Brush.linearGradient(
                            colors =
                                listOf(
                                    MidasColors.Purple.extraDark,
                                    MidasColors.Blue.dark,
                                    MidasColors.Blue.kindaDark,
                                ),
                            start = Offset(0f, 0f),
                            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY),
                        ),
                ),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = MidasColors.Purple.light.copy(alpha = 0.12f),
                radius = 150.dp.toPx(),
                center = Offset(size.width * 0.9f, size.height * 0.1f),
            )
        }
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TotalSavedInfo(summary)
            CompletionRing(summary.completionPercent)
        }
    }
}

@Composable
private fun CompletionRing(completionPercent: Int) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
        CircularProgressIndicator(
            progress = { completionPercent / 100f },
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            trackColor = Color.White.copy(alpha = 0.2f),
            strokeWidth = 5.dp,
        )
        Text(
            text = "$completionPercent%",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun TotalSavedInfo(summary: GoalsSummary) {
    Column {
        Row {
            Text(
                text = stringResource(total_saved),
                fontWeight = FontWeight.W300,
                fontSize = 14.sp,
                color = Color.White,
            )
        }
        Row(
            modifier =
                Modifier
                    .padding(bottom = 20.dp),
        ) {
            Text(
                text = summary.totalSaved.toCurrency(),
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }
        Row {
            Column {
                Text(
                    text = stringResource(goals_active_count, summary.activeGoalsCount),
                    color = Color.White,
                    fontWeight = FontWeight.W300,
                )
            }
        }
    }
}

@CustomPreview
@Composable
fun BalanceGoalStatusCardPreview() {
    MidasLightPreview {
        GoalCardBalanceStatus(
            summary =
                GoalsSummary(
                    totalSaved = 12450.0,
                    completionPercent = 78,
                    activeGoalsCount = 4,
                ),
        )
    }
}
