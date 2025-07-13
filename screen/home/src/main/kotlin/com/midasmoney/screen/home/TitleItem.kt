package com.midasmoney.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midasmoney.shared.ui.core.MidasPreview
import com.midasmoney.shared.ui.core.Theme

@Composable
fun TitleItem(textTitle: String, textButton: String, actionButton: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 25.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = textTitle,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
                )
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .padding(end = 10.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = actionButton
            ) {
                Text(
                    text = textButton,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun TitleItemPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        TitleItem(
            textTitle = "Recent Transactions",
            textButton = "View All",
            actionButton = {}
        )
    }
}