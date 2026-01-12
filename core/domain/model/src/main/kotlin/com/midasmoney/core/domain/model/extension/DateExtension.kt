package com.midasmoney.core.domain.model.extension

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinLocalTime
import kotlinx.datetime.toLocalDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate as KtLocalDate
import kotlinx.datetime.LocalTime as KtLocalTime

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
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm[:ss.SSSSSS]"))
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

@OptIn(ExperimentalTime::class)
fun Clock.System.getCurrentLocalDate(): KtLocalDate {
    return this.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

@OptIn(ExperimentalTime::class)
fun Clock.System.getCurrentTime(): KtLocalTime {
    return this.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
}
