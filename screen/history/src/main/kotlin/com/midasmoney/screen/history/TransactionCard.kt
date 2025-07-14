package com.midasmoney.screen.history

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.midasmoney.shared.model.data.TransactionHistoryItem
import com.midasmoney.shared.model.mock.Database
import com.midasmoney.shared.ui.core.component.MidasCard
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview
import com.midasmoney.shared.ui.core.values.formatAmount
import com.midasmoney.shared.ui.core.values.formatAmountColor
import com.midasmoney.shared.ui.core.values.formatDate
import com.midasmoney.shared.ui.core.values.formatIcon
import com.midasmoney.shared.ui.core.values.formatIconColor
import com.midasmoney.shared.ui.core.values.formatIconColorBackground

@Composable
fun TransactionCard(
    transactionHistoryItem: TransactionHistoryItem
) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 18.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Icon(
                            contentDescription = transactionHistoryItem.formatIcon().name,
                            imageVector = transactionHistoryItem.formatIcon(),
                            tint = transactionHistoryItem.formatIconColor(),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = transactionHistoryItem.formatIconColorBackground())
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
                                text = transactionHistoryItem.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row {
                            Text(
                                text = transactionHistoryItem.category.name,
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier
                                    .padding(end = 4.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .clip(CircleShape)
                                    .size(5.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.outline
                                    )
                            )
                            Text(
                                text = transactionHistoryItem.formatDate(),
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.outline,
                                fontWeight = FontWeight.W400,
                                modifier = Modifier
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier
                            .weight(0.3f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = transactionHistoryItem.formatAmount(),
                            color = transactionHistoryItem.formatAmountColor(),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = transactionHistoryItem.status.toString()
                                .lowercase()
                                .replaceFirstChar { it.uppercaseChar() },
                            color = MaterialTheme.colorScheme.outline,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .padding(start = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TransactionCardLightPreview() {
    MidasLightPreview {
        TransactionCard(
            transactionHistoryItem = Database.transactionHistoryList.first(),
        )
    }
}

@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TransactionCardDarkPreview() {
    MidasDarkPreview {
        TransactionCard(
            transactionHistoryItem = Database.transactionHistoryList.first(),
        )
    }
}