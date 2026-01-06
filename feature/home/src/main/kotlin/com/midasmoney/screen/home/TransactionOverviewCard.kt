package com.midasmoney.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.TransactionReport
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.extension.toCurrency
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasTheme

@Composable
fun TransactionOverviewCard(
    transactionReport: TransactionReport,
) {
    MidasCard(
        modifier = Modifier
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
                        text = transactionReport.amount.toCurrency(),
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

@CustomPreview
@Composable
private fun TransactionOverviewLightPreview() {
    MidasTheme {
        TransactionOverviewCard(
            transactionReport = Database.transactionReportIncome
        )
    }
}
