package com.midasmoney.core.domain.model.extension

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlinx.datetime.toKotlinLocalTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalTime as KtLocalTime
import kotlinx.datetime.LocalDate as KtLocalDate

fun KtLocalTime.formatTime(): String {
    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        this.hour,
        this.minute
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.formatTime(): String {
    return String.format(
        Locale.getDefault(),
        "%02d:%02d",
        this.hour,
        this.minute
    )
}

fun String.toKtLocalTime(): KtLocalTime {
    val localTime =
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    return localTime.toKotlinLocalTime()
}

fun LocalDate.formatDate(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
}

@OptIn(ExperimentalTime::class)
fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
}

fun String.toLocalDate(): KtLocalDate {
    return KtLocalDate.parse(this, KtLocalDate.Formats.ISO)
}
