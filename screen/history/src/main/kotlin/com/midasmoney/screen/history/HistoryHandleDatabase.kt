package com.midasmoney.screen.history

import android.util.Log
import com.midasmoney.core.data.model.Transaction
import com.midasmoney.core.data.model.TransactionType
import com.midasmoney.core.data.mock.Database
import java.time.LocalDate

object HistoryHandleDatabase {
    fun getTransactionsGroupByDate(
        filter: String,
        amount: Int,
        type: TransactionType? = null
    ): Map<LocalDate, List<Transaction>> {
        Log.d(
            "HistoryHandleDatabase",
            "getTransactionsGroupByDate - filter: $filter, amount: $amount, type: $type"
        )
        return Database.transactions
            // sort by data
            .sortedByDescending { it.date }
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
                || it.category.name.lowercase().contains(stringSearch)
                || it.description.lowercase().contains(stringSearch)
                || it.amount.toString().lowercase().contains(stringSearch)
                || it.status.toString().lowercase().contains(stringSearch) else true
    }
}

fun List<Transaction>.filterByType(pType: TransactionType?): List<Transaction> {
    return filter { if (pType != null) it.type == pType else true }
}

fun List<Transaction>.groupAndFilterByDays(amount: Int): Map<LocalDate, List<Transaction>> {
    return groupBy { it.date }.filter { it.key >= LocalDate.now().minusDays(amount.toLong()) }
}