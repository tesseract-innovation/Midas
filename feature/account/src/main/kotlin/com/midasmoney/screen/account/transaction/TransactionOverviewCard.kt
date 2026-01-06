package com.midasmoney.screen.account.transaction

import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.TransactionReport
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview

@Composable
fun TransactionOverviewCard(
    transactionReport: TransactionReport,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)),
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(.85f)
            ) {
                Row {
                    Text(
                        text = transactionReport.type.name.lowercase()
                            .replaceFirstChar { it.uppercase() }
                    )
                }
                Row(
                    Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text =  transactionReport.amount.toCurrency(),
                        fontWeight = FontWeight.Bold,
                    )
                }
                Row {
                    Text(
                        text = "+${transactionReport.percentage * 100}%",
                        color = if (transactionReport.type == TransactionType.INCOME) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(.15f)
            ) {
                Icon(
                    imageVector = if (transactionReport.type == TransactionType.INCOME) Icons.Outlined.ArrowDownward else Icons.Outlined.ArrowUpward,
                    tint = if (transactionReport.type == TransactionType.INCOME) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TransactionOverviewCardLightPreview() {
   MidasLightPreview {
        TransactionOverviewCard(
            transactionReport = Database.transactionReportIncome
        )
    }
}

@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TransactionOverviewCardDarkPreview() {
   MidasDarkPreview {
        TransactionOverviewCard(
            transactionReport = Database.transactionReportExpense,
        )
    }
}
