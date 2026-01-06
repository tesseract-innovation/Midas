package com.midasmoney.screen.goals.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.ui.theme.MidasColors
import com.midasmoney.core.ui.component.MidasCard
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview

@Composable
fun CompletedGoalCard(
) {
    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 12.dp, end = 20.dp)
    ) {
        MidasCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 17.dp, top = 19.dp, bottom = 12.dp, end = 17.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = Icons.Filled.AccountBalanceWallet.name,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                                .padding(10.dp),
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.7f)
                ) {
                    Row {
                        Text(
                            text = "Emergency Fund",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Row {
                        Text(
                            text = "Completed on Jan 2024",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Row {
                        Text(
                            text = "$5,000",
                            color = MidasColors.Green.primary,
                            fontWeight = FontWeight.W400
                        )
                    }
                    Row {
                        Text(
                            text = "Saved",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun CompletedGoalCardLightPreview() {
    MidasLightPreview {
        CompletedGoalCard()
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun CompletedGoalCardDarkPreview() {
    MidasDarkPreview {
        CompletedGoalCard()
    }
}
