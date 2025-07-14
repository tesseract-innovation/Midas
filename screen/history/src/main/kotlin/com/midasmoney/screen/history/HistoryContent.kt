package com.midasmoney.screen.history

import android.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.shared.ui.core.MidasPreview
import com.midasmoney.shared.ui.core.Theme

@Composable
fun HistoryContentImp() {
    HistoryContent()
}

@Composable
fun HistoryContent(
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
//                TransactionCard(
//                    transactionHistoryItem = Database.transactionHistoryList[4],
//                    isDarkTheme = isDarkTheme

            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.CYAN.toLong())
@Composable
fun HomeContentImpLightPreview() {
    MidasPreview(
        theme = Theme.LIGHT
    ) {
        HistoryContent()
    }
}

@Preview(showBackground = true, backgroundColor = Color.DKGRAY.toLong())
@Composable
fun HomeContentImpDarkPreview() {
    MidasPreview(
        theme = Theme.DARK
    ) {
        HistoryContent(isDarkTheme = true)
    }
}