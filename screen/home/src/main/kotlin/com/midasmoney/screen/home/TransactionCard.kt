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
import java.time.LocalDate

@Composable
fun TransactionCard(
    transactionHistoryItem: TransactionHistoryItem,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            isDarkTheme = isDarkTheme
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Icon(
                            imageVector = transactionHistoryItem.category?.icon ?: Icons.Filled.Category,
                            contentDescription = "Card transaction icon",
                            tint = transactionHistoryItem.category?.color ?: MidasColors.Green.primary,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(transactionHistoryItem.category?.color?.copy(alpha = 0.3f) ?: MidasColors.Green.primary.copy(alpha = 0.3f))
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
                                text = transactionHistoryItem.title.toString(),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row {
                            Text(
                                text = DateTimeUtils.formatDate(transactionHistoryItem.date ?: LocalDate.now()),
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
                            text = if (transactionHistoryItem.type == TransactionType.Expense) "-$${transactionHistoryItem.amount}".replace(
                                '.',
                                ','
                            ) else "+$${transactionHistoryItem.amount}".replace('.', ','),
                            color = if (transactionHistoryItem.type == TransactionType.Expense) MidasColors.Red.primary else MidasColors.Green.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TransactionCardLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        TransactionCard(
            transactionHistoryItem = Database.transactionHistoryList.first(),
        )
    }
}

@Preview
@Composable
fun TransactionCardDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        TransactionCard(
            transactionHistoryItem = Database.transactionHistoryList.first(),
            isDarkTheme = true
        )
    }
}