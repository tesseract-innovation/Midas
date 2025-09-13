package com.midasmoney.screen.history.tab

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthWest
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.screen.history.HistoryViewModel
import com.midasmoney.core.resource.R.string.all
import com.midasmoney.core.resource.R.string.expense
import com.midasmoney.core.resource.R.string.income
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TabRow(viewModel: HistoryViewModel, tabs: List<TabItem>) {
    val selectedTab by viewModel.selectedTabIndex.collectAsState()
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 5.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            divider = {},
            indicator = {},
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tab ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp)
                ) {
                    Tab(
                        text = {
                            Text(
                                text = tab.title,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                tint = when (tab.title) {
                                    stringResource(income) -> MidasColors.Green.primary
                                    stringResource(expense) -> MidasColors.Red.primary
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        },
                        selected = selectedTab == index,
                        onClick = { viewModel.setSelectedTab(index) },
                        modifier = Modifier
                            .height(50.dp)
                            .background(
                                if (selectedTab == index)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                            )
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TabRowLightPreview() {
    val viewModel = HistoryViewModel()
    val tabs = listOf(
        TabItem(stringResource(all), Icons.Default.AllInbox) { Tab(viewModel) },
        TabItem(stringResource(income), Icons.Default.NorthEast) { Tab(viewModel) },
        TabItem(stringResource(expense), Icons.Default.SouthWest) { Tab(viewModel) }
    )
    MidasLightPreview {
        TabRow(viewModel, tabs)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TabRowDarkPreview() {
    val viewModel = HistoryViewModel()
    val tabs = listOf(
        TabItem(stringResource(all), Icons.Default.AllInbox) { Tab(viewModel) },
        TabItem(stringResource(income), Icons.Default.NorthEast) { Tab(viewModel) },
        TabItem(stringResource(expense), Icons.Default.SouthWest) { Tab(viewModel) }
    )
    MidasDarkPreview {
        TabRow(viewModel, tabs)
    }
}
