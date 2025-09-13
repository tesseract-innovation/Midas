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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.data.model.Balance
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.resource.R.string.total_balance
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview
import com.midasmoney.core.ui.values.toCurrency

@Composable
fun BalanceStatus(
    balance: Balance,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val gradientColors = if (isDarkTheme) {
        listOf(
            MidasColors.Purple.primary.copy(alpha = 0.7f),
            MidasColors.Green.primary.copy(alpha = 0.5f)
        )
    } else {
        listOf(
            MidasColors.Purple.primary,
            MidasColors.Green.primary
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
                        text = stringResource(total_balance),
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
                        text = balance.totalValue.toCurrency(),
                        fontSize = 28.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    Column {
                        Text(
                            text = stringResource(income),
                            color = Color.White,
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp

                        )
                        Text(
                            text = balance.incomeValue.toCurrency(),
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
                            text = stringResource(expense),
                            color = Color.White,
                            fontWeight = FontWeight.W300,
                            fontSize = 13.sp
                        )
                        Text(
                            text = balance.expenseValue.toCurrency(),
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
                        contentDescription = Icons.Filled.AccountBalanceWallet.name,
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
                            .background(color = Color.LightGray.copy(alpha = 0.5f))
                            .padding(11.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.RemoveRedEye,
                            contentDescription = Icons.Filled.RemoveRedEye.name,
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

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun BalanceStatusLightPreview() {
    MidasLightPreview {
        BalanceStatus(
            balance = Database.balance
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun BalanceStatusDarkPreview() {
    MidasDarkPreview {
        BalanceStatus(
            balance = Database.balance,
            isDarkTheme = true
        )
    }
}