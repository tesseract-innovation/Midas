package com.midasmoney.core.domain.model.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.converter.ColorConverter
import com.midasmoney.core.util.DateTimeUtils
import kotlin.time.ExperimentalTime

private val expenses = listOf(
    TransactionType.WITHDRAWAL,
    TransactionType.FEES,
    TransactionType.REFUND,
    TransactionType.LOAN_PAYMENT,
    TransactionType.INTEREST,
    TransactionType.TAX,
    TransactionType.EXPENSE
)

fun Transaction.formatAmount(): String {
    return this.amount.toCurrency()
}

fun Transaction.formatAmountValue(): Double {
    return if (this.type in expenses && this.amount > 0.0) this.amount * (-1) else this.amount
}

@Composable
fun Transaction.formatAmountColor(): Color {
    return if (this.type in expenses) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
}


@OptIn(ExperimentalTime::class)
fun Transaction.formatDate(): String {
    return DateTimeUtils.formatDate(this.date.toLocalDate())
}

fun Transaction.formatIconColorBackground(): Color {
    return ColorConverter.aRgbToColor(this.color).copy(alpha = 0.2f)
}
