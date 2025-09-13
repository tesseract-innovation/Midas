package com.midasmoney.screen.history

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.ui.component.MidasDarkPreview
import com.midasmoney.core.ui.component.MidasLightPreview
import com.midasmoney.core.resource.R.array.chip_days

@Composable
fun TimeSelector(viewModel: HistoryViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var selectedChip by remember { mutableStateOf<String?>(viewModel.daysState.value.toDays()) }
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 20.dp, top=6.dp, end = 20.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val chips = stringArrayResource(chip_days)
                chips.forEach { chipText ->
                    FilterChip(
                        selected = (selectedChip == chipText),
                        onClick = {
                            selectedChip = chipText
                            viewModel.setDays(chipText.toDays())
                        },
                        label = { Text(text = chipText) }
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.WHITE.toLong())
@Composable
fun TimeSelectorLightPreview() {
    MidasLightPreview {
        val viewModel = HistoryViewModel()
        TimeSelector(viewModel)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, backgroundColor = Color.BLACK.toLong())
@Composable
fun TimeSelectorDarkPreview() {
    MidasDarkPreview {
        val viewModel = HistoryViewModel()
        TimeSelector(viewModel)
    }
}