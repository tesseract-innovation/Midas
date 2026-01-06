package com.midasmoney.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.domain.model.converter.IconConverter
import com.midasmoney.core.domain.model.extension.formatAmount
import com.midasmoney.core.domain.model.extension.formatAmountColor
import com.midasmoney.core.domain.model.extension.formatDate
import com.midasmoney.core.domain.model.extension.formatIconColorBackground
import com.midasmoney.core.ui.preview.CustomPreview
import com.midasmoney.core.ui.theme.MidasTheme

@Composable
fun TransactionCard(
    transaction: Transaction,
    onClick: (() -> Unit)? = null,
    paddingTop: Dp = 10.dp,
    paddingStart: Dp = 20.dp,
    paddingEnd: Dp = 20.dp
) {
    Column(
        modifier = Modifier
            .padding(top = paddingTop, start = paddingStart, end = paddingEnd)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable {
                    onClick?.invoke()
                }
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
                            .padding(end = 12.dp)
                    ) {
                        Icon(
                            contentDescription = transaction.title,
                            imageVector = IconConverter.getImageVector(transaction.icon),
                            tint = ColorConverter.aRgbToColor(transaction.color),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color = transaction.formatIconColorBackground())
                                .padding(10.dp)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.Start
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
                                text = transaction.type.displayName,
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
                                text = transaction.formatDate(),
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
                            text = transaction.formatAmount(),
                            color = transaction.formatAmountColor(),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = transaction.status.displayName,
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

@CustomPreview
@Composable
fun TransactionCardPreview() {
    MidasTheme {
        TransactionCard(
            transaction = Database.transactions.first(),
            onClick = {}
        )
    }
}
