package com.midasmoney.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun BalanceStatus() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Total Balance",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "$4,250.00",
                fontSize = 38.sp,
                color = Color.White
            )
        }

        Row {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Income",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "+$3,200",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
            Column(
                Modifier.padding(horizontal = 8.dp)
            ) { }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Expense",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "-$1,850",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}