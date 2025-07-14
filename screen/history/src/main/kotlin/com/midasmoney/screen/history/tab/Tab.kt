package com.midasmoney.screen.history.tab

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.midasmoney.screen.history.HistoryViewModel
import com.midasmoney.screen.history.TimeTitle
import com.midasmoney.screen.history.TransactionCard
import com.midasmoney.shared.ui.core.component.MidasDarkPreview
import com.midasmoney.shared.ui.core.component.MidasLightPreview

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Tab(viewModel: HistoryViewModel) {
    val transactions by viewModel.combinedState.collectAsState()
    Column {
        transactions.forEach { (date, list) ->
            TimeTitle(title = date)
            list.forEach { TransactionCard(it) }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TabLightPreview() {
    MidasLightPreview {
        val viewModel = HistoryViewModel()
        Tab(viewModel)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TabDarkPreview() {
    MidasDarkPreview {
        val viewModel = HistoryViewModel()
        Tab(viewModel)
    }
}