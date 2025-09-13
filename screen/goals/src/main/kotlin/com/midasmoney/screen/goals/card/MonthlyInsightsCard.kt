package com.midasmoney.screen.goals.card

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.resource.R.string.monthly_insights
import com.midasmoney.core.resource.R.string.monthly_save
import com.midasmoney.core.resource.R.string.monthly_target
import com.midasmoney.core.resource.R.string.monthly_track
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasGradientCard
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun MonthlyInsights(
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    MidasGradientCard(
        primaryColor = MidasColors.Orange.primary,
        secondaryColor = MidasColors.Pink.primary,
        height = 163.dp,
        isDarkTheme = isDarkTheme
    ) {
        Column {
            Row(
                modifier = Modifier.padding(start = 19.dp, top = 24.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Outlined.Analytics,
                        contentDescription = Icons.Outlined.Analytics.name,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(44.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(monthly_insights),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Text(
                            text = stringResource(monthly_track),
                            fontWeight = FontWeight.W300,
                            fontSize = 12.sp,
                            color = Color.White,
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, bottom = 28.dp, top = 18.dp)
            ) {
                Column {
                    Row {
                        Text(
                            text = stringResource(monthly_save),
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp,
                            color = Color.White,
                        )
                    }
                    Row {
                        Text(
                            text = "$850",
                            fontSize = 21.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W400
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 46.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(monthly_target),
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp,
                            color = Color.White,
                        )
                    }
                    Row {
                        Text(
                            text = "$850",
                            fontSize = 21.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W400
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun MonthlyInsightsLightPreview() {
    MidasLightPreview {
        MonthlyInsights()
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun MonthlyInsightsDarkPreview() {
    MidasDarkPreview {
        MonthlyInsights(
            isDarkTheme = true
        )
    }
}