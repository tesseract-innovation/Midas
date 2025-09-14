package com.midasmoney.screen.history

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.screen.history.tab.TabNavigation
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview

@Composable
fun HistoryContentImp(paddingValues: PaddingValues) {
    HistoryContent(paddingValues)
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun HistoryContent(paddingValues: PaddingValues) {
    val viewModel = HistoryViewModel()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                TabNavigation(viewModel)
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = Color.LTGRAY.toLong())
@Composable
fun HomeContentImpLightPreview() {
    MidasLightPreview {
        val paddingValues = PaddingValues()
        HistoryContent(paddingValues)
    }
}

@Preview(showBackground = true, backgroundColor = Color.DKGRAY.toLong())
@Composable
fun HomeContentImpDarkPreview() {
    MidasDarkPreview {
        val paddingValues = PaddingValues()
        HistoryContent(paddingValues)
    }
}