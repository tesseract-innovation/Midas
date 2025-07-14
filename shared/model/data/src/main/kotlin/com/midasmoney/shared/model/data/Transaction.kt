package com.midasmoney.shared.model.data

import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

enum class TransactionType {
    Expense,
    Income
}

data class Category(
    val name: String,
    val type: TransactionType,
    val description: String? = null,
    val icon: IconModel? = null,
    val color: Int? = null,
    val id: UUID = UUID.randomUUID()
)

data class TransactionHistoryItem(
    val accountId: UUID,
    val type: TransactionType,
    val amount: BigDecimal,
    val title: String? = null,
    val description: String? = null,
    val dateTime: Instant? = null,
    val dueDate: Instant? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val paidFor: Instant? = null,
    val category: Category? = null,
    val id: UUID = UUID.randomUUID()
)