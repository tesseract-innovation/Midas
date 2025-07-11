package com.midasmoney.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.shared.ui.core.MidasPreview
import com.midasmoney.shared.ui.core.Theme

@Composable
fun BalanceStatus(
    totalValue: String,
    incomeValue: String,
    expenseValue: String,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val gradientColors = if (isDarkTheme) {
        listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 5.dp)
            .height(180.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(start = 28.dp, top = 25.dp)
            ) {
                Row {
                    Text(
                        text = "Total Balance",
                        fontWeight = FontWeight.W300,
                        fontSize = 14.sp,
                        color = Color.White,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = totalValue,
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    Column {
                        Text(
                            text = "Income",
                            color = Color.White,
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp

                        )
                        Text(
                            text = incomeValue,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .height(0.dp)
                    )
                    Column {
                        Text(
                            text = "Expense",
                            color = Color.White,
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp
                        )
                        Text(
                            text = expenseValue,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp, end = 25.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountBalanceWallet,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .alpha(0.8f),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .size(40.dp)
                            .background(
                                color = Color(
                                    Color.LightGray.red,
                                    Color.LightGray.green,
                                    Color.LightGray.blue,
                                    0.2f
                                )
                            )
                            .padding(11.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BalanceStatusLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        BalanceStatus(
            totalValue = "$14,250.50",
            incomeValue = "+$3,200",
            expenseValue = "-$1,850"
        )
    }
}

@Preview
@Composable
fun BalanceStatusDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        BalanceStatus(
            totalValue = "$14,250.50",
            incomeValue = "+$3,200",
            expenseValue = "-$1,850",
            isDarkTheme = true
        )
    }
}