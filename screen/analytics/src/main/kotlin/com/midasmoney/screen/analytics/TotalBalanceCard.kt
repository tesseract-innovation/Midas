package com.midasmoney.screen.analytics

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.core.resource.R.string.total_balance
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasGradientCard
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun TotalBalanceCard(
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    MidasGradientCard(
        primaryColor = MidasColors.Blue.primary,
        secondaryColor = MidasColors.Gray,
        height = 140.dp,
        isDarkTheme = isDarkTheme
    ) {
        Column(
            modifier = Modifier
                .padding(start = 15.dp, top = 19.dp)
        ) {
            Row {
                Text(
                    text = stringResource(total_balance),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "$24,850.32",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Row (
                modifier = Modifier
                    .padding(top = 3.dp)
            ){
                Text(
                    text = "+12.5% from last week",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun TotalBalanceCardLightPreview() {
    MidasLightPreview {
        TotalBalanceCard(
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun TotalBalanceCardDarkPreview() {
    MidasDarkPreview {
        TotalBalanceCard(
            isDarkTheme = true
        )
    }
}