package com.midasmoney.screen.history

import android.util.Log
import com.midasmoney.core.data.mock.Database
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionType
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toKotlinLocalDate
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object HistoryHandleDatabase {
    private val TAG: String = HistoryHandleDatabase.javaClass.name

    @OptIn(ExperimentalTime::class)
    fun getTransactionsGroupByDate(
        filter: String,
        amount: Int,
        type: TransactionType? = null
    ): Map<Instant, List<Transaction>> {
        Log.d(TAG, "getTransactionsGroupByDate - filter: $filter, amount: $amount, type: $type")
        return Database.transactions
            // sort by data
            .sortedByDescending { it.createAt }
            // filter by tab
            .filterByType(type)
            // filter by string
            .filterBySearch(filter)
            // group and filter by days
            .groupAndFilterByDays(amount)
    }
}

fun List<Transaction>.filterBySearch(stringSearch: String): List<Transaction> {
    return filter {
        if (stringSearch.isNotEmpty()) it.title.lowercase().contains(stringSearch)
                || it.description.lowercase().contains(stringSearch)
                || it.amount.toString().lowercase().contains(stringSearch)
                || it.status.toString().lowercase().contains(stringSearch) else true
    }
}

fun List<Transaction>.filterByType(pType: TransactionType?): List<Transaction> {
    return filter { if (pType != null) it.type == pType else true }
}

@OptIn(ExperimentalTime::class)
fun List<Transaction>.groupAndFilterByDays(amount: Int): Map<Instant, List<Transaction>> {
    val cutoffDate = java.time.LocalDate.now().minusDays(amount.toLong()).toKotlinLocalDate()
    val instant = cutoffDate.atStartOfDayIn(TimeZone.UTC)
    return groupBy { it.createAt }.filter { it.key >= instant }
}
