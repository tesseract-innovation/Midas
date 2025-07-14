package com.midasmoney.screen.history

import com.midasmoney.shared.model.data.TransactionHistoryItem
import com.midasmoney.shared.model.data.TransactionType
import com.midasmoney.shared.model.mock.Database
import java.time.LocalDate

object HistoryHandleDatabase {
    private fun getTransactionsForAmount(
        transactions: List<TransactionHistoryItem>,
        amount: Int
    ): Map<LocalDate, List<TransactionHistoryItem>> {
        val endInclusive = if (amount < transactions.size) amount - 1 else transactions.size - 1
        return transactions
            .sortedByDescending { it.date }
            .slice(IntRange(0, endInclusive))
            .groupBy { it.date }
    }

    fun getTransactionsGroupByDate(
        filter: String,
        amount: Int,
        type: TransactionType? = null
    ): Map<LocalDate, List<TransactionHistoryItem>> {
           val endInclusive = if (amount < Database.transactionHistoryList.size) amount - 1 else Database.transactionHistoryList.size - 1
        val transactions = Database.transactionHistoryList
            .sortedByDescending { it.date }
            .slice(IntRange(0, endInclusive))
            .filter { if (type != null) it.type == type else true }
            .filter {
                if (filter.isNotEmpty()) it.title.lowercase().contains(filter)
                        || it.category.name.lowercase().contains(filter)
                        || it.description.lowercase().contains(filter)
                        || it.amount.toString().lowercase().contains(filter)
                        || it.status.toString().lowercase().contains(filter) else true
            }
        return getTransactionsForAmount(transactions, amount)
    }
}