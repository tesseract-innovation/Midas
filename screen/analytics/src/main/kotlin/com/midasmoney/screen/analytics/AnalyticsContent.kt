package com.midasmoney.screen.analytics

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview

@Composable
fun AnalyticsContentImp() {
    AnalyticsContent()
}

@Composable
fun AnalyticsContent(
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    var selectedChip by remember { mutableStateOf<String?>("7D") }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 100.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 19.dp)
                ) {
                    val chips = listOf("7D", "1M", "3M", "6M", "1Y")
                    chips.forEach { chipText ->
                        FilterChip(
                            selected = (selectedChip == chipText),
                            onClick = { selectedChip = chipText },
                            label = { Text(text = chipText) }
                        )
                    }
                }
                TotalBalanceCard(isDarkTheme)
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Row {
                        Column {
                            Row {
                                Text(
                                    text = "Income"
                                )
                            }
                            Row {
                                Text(
                                    text = "$8,420"
                                )
                            }
                            Row {
                                Text(
                                    text = "+8.2%"
                                )
                            }
                        }
                        Column {
                            Icon(
                                imageVector = Icons.Outlined.ArrowUpward,
                                contentDescription = ""
                            )
                        }
                    }
                    Row {
                        Column {
                            Row {
                                Text(
                                    text = "Expenses"
                                )
                            }
                            Row {
                                Text(
                                    text = "$3.240"
                                )
                            }
                            Row {
                                Text(
                                    text = "+2.1%"
                                )
                            }
                        }
                        Column {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDownward,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.WHITE.toLong())
@Composable
fun AnalyticsScreenLightPreview() {
    MidasLightPreview {
        AnalyticsContent(
        )
    }
}

@Preview(showBackground = true, backgroundColor = android.graphics.Color.BLACK.toLong())
@Composable
fun AnalyticsScreenDarkPreview() {
    MidasDarkPreview {
        AnalyticsContent(
            isDarkTheme = true
        )
    }
}