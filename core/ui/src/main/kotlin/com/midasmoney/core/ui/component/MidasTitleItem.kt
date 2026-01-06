package com.midasmoney.core.ui.component

import android.graphics.Color
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
import com.midasmoney.core.ui.preview.MidasDarkPreview
import com.midasmoney.core.ui.preview.MidasLightPreview

@Composable
fun MidasTitleItem(
    textTitle: String,
    textButton: String? = null,
    actionButton: (() -> Unit)? = null
) {
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
            textButton?.let { text ->
                actionButton?.let { action ->
                    TextButton(
                        onClick = action
                    ) {
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TitleItemLightPreview() {
    MidasLightPreview {
        MidasTitleItem(
            textTitle = "Recent Transactions",
            textButton = "View All",
            actionButton = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TitleItemDarkPreview() {
    MidasDarkPreview {
        MidasTitleItem(
            textTitle = "Recent Transactions",
            textButton = "View All",
            actionButton = {}
        )
    }
}
