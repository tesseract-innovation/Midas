package com.midasmoney.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTimeUtils {
    @OptIn(ExperimentalTime::class)
    fun formatDate(localDate: LocalDate): String {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        if (localDate == today) return "Today"
        val isMoreThenOneWeek = localDate.daysUntil(today) > 7
        return when (isMoreThenOneWeek) {
            true -> formatToMoreThenOneWeek(localDate)
            false -> formatToLessThenOneWeek(localDate)
        }
    }

    private fun formatToMoreThenOneWeek(localDate: LocalDate): String {
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val javaDate = java.util.Calendar.getInstance().apply {
            set(localDate.year, localDate.month.number - 1, localDate.day)
        }.time
        return formatter.format(javaDate)
    }

    private fun formatToLessThenOneWeek(localDate: LocalDate): String {
        val formatterDay = SimpleDateFormat("EEEE", Locale.getDefault())
        val javaDate = java.util.Calendar.getInstance().apply {
            set(localDate.year, localDate.month.number - 1, localDate.day)
        }.time
        return formatterDay.format(javaDate)
    }
}
