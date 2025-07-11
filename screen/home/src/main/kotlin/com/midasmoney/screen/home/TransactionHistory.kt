package com.midasmoney.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.midasmoney.shared.ui.core.MidasColors
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
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val type: TransactionType,
    val description: String? = null,
    val icon: ImageVector = Icons.Filled.Category,
    val color: Color = MidasColors.Green.primary
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