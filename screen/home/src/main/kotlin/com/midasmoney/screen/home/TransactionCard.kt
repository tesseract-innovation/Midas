package com.midasmoney.screen.home

import androidx.compose.foundation.background
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
import com.midasmoney.core.data.model.Transaction
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview
import com.midasmoney.core.ui.values.formatAmount
import com.midasmoney.core.ui.values.formatAmountColor
import com.midasmoney.core.ui.values.formatDate
import com.midasmoney.core.ui.values.formatIcon
import com.midasmoney.core.ui.values.formatIconColor
import com.midasmoney.core.ui.values.formatIconColorBackground

@Composable
fun TransactionCard(
    transaction: Transaction
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
                        .padding(start = 15.dp, end = 18.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Icon(
                            contentDescription = transaction.formatIcon().name,
                            imageVector = transaction.formatIcon(),
                            tint = transaction.formatIconColor(),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = transaction.formatIconColorBackground())
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
                                text = transaction.title,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Row {
                            Text(
                                text = transaction.formatDate(),
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.outline,
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
                            text = transaction.formatAmount(),
                            color = transaction.formatAmountColor(),
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
    MidasLightPreview {
        TransactionCard(
            transaction = Database.transactions.first()
        )
    }
}

@Preview
@Composable
fun TransactionCardDarkPreview() {
    MidasDarkPreview {
        TransactionCard(
            transaction = Database.transactions.first()
        )
    }
}