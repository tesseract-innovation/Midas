package com.midasmoney.core.ui.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.midasmoney.core.domain.model.extension.formatDate
import com.midasmoney.core.domain.model.extension.toLocalDate
import com.midasmoney.core.resource.R

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun midasDatePicker(): String {
    var showDatePicker by remember { mutableStateOf(true) }
    val dataPickerState = rememberDatePickerState()
    val selectedDate = dataPickerState.selectedDateMillis?.toLocalDate()?.formatDate()
        ?: stringResource(R.string.no_selected_date)

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(
                state = dataPickerState,
                showModeToggle = false
            )
        }
    }
    return if (!showDatePicker && selectedDate != stringResource(R.string.no_selected_date)) {
        selectedDate
    } else {
        return ""
    }
}
