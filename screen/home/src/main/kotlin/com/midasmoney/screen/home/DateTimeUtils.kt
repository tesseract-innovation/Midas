package com.midasmoney.screen.home

import androidx.compose.ui.text.intl.Locale
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

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
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.current.platformLocale)
        return localDate.format(formatter)
    }

    private fun formatToLessThenOneWeek(localDate: LocalDate): String {
        val formatterDay = DateTimeFormatter.ofPattern("EEEE", Locale.current.platformLocale)
        val day = localDate.format(formatterDay)
        return day
    }
}