package com.midasmoney.core.ui.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateTimeUtils {
    fun formatDate(localDate: LocalDate): String {
        val today = LocalDate.now()
        if (localDate == today) return "Today"
        val isMoreThenOneWeek = ChronoUnit.DAYS.between(localDate, today) > 7
        return when (isMoreThenOneWeek) {
            true -> formatToMoreThenOneWeek(localDate)
            false -> formatToLessThenOneWeek(localDate)
        }
    }

    private fun formatToMoreThenOneWeek(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.getDefault())
        return localDate.format(formatter)
    }

    private fun formatToLessThenOneWeek(localDate: LocalDate): String {
        val formatterDay = DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
        val day = localDate.format(formatterDay)
        return day
    }
}