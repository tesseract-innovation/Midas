package com.midasmoney.screen.goals.card

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Adjust
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.shared.model.data.Balance
import com.midasmoney.shared.model.mock.Database
import com.midasmoney.shared.resource.R.string.total_saved
import com.midasmoney.shared.ui.core.color.MidasColors
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasGradientCard
import com.midasmoney.shared.ui.core.component.MidasLightPreview
import com.midasmoney.shared.ui.core.values.toCurrency

@Composable
fun BalanceGoalStatusCard(
    balance: Balance,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    MidasGradientCard(
        primaryColor = MidasColors.Blue.primary,
        secondaryColor = MidasColors.Purple.primary,
        height = 140.dp,
        isDarkTheme = isDarkTheme
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(start = 28.dp, top = 25.dp)
            ) {
                Row {
                    Text(
                        text = stringResource(total_saved),
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
                            text = "4 Active Goals",
                            color = Color.White,
                            fontWeight = FontWeight.W300
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
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Adjust,
                        contentDescription = Icons.Rounded.Adjust.name,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .size(53.dp)
                            .alpha(0.8f),
                    )
                }
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    Text(
                        text = "78% Complete", color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun BalanceGoalStatusCardLightPreview() {
    MidasLightPreview {
        BalanceGoalStatusCard(
            balance = Database.balance,
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun BalanceGoalStatusCardDarkPreview() {
    MidasDarkPreview {
        BalanceGoalStatusCard(
            balance = Database.balance,
            isDarkTheme = true
        )
    }
}