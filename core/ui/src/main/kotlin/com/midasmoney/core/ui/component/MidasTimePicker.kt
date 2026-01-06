package com.midasmoney.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.midasmoney.core.domain.model.extension.formatTime
import com.midasmoney.core.resource.R

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun midasTimePicker(): String {
    var showTimePicker by remember { mutableStateOf(true) }
    val timePickerState = rememberTimePickerState()
    val selectedTime = timePickerState.formatTime()

    if (showTimePicker) {
        DatePickerDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showTimePicker = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePicker = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), // ou .fillMaxSize() se quiser vertical tb
                contentAlignment = Alignment.Center
            ) {
                TimePicker(
                    state = timePickerState
                )
            }
        }
    }

    return if (!showTimePicker) {
        selectedTime
    } else {
        ""
    }
}
