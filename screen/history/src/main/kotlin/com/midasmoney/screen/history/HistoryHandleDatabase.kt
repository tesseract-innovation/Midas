package com.midasmoney.screen.history

import com.midasmoney.shared.model.data.Transaction
import com.midasmoney.shared.model.data.TransactionType
import com.midasmoney.shared.model.mock.Database
import java.time.LocalDate

object HistoryHandleDatabase {
    private fun getTransactionsForAmount(
        transactions: List<Transaction>,
        amount: Int
    ): Map<LocalDate, List<Transaction>> {
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
    ): Map<LocalDate, List<Transaction>> {
           val endInclusive = if (amount < Database.transactions.size) amount - 1 else Database.transactions.size - 1
        val transactions = Database.transactions
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