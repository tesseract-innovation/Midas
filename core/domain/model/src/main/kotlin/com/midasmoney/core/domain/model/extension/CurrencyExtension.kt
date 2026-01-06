package com.midasmoney.core.domain.model.extension

import java.text.NumberFormat
import java.util.Locale

/**
 * Extension function to format a Double as currency.
 * Uses the default locale for formatting.
 * 
 * Example:
 * ```
 * val amount = 1234.56
 * val formatted = amount.toCurrency() // Returns "R$ 1.234,56" for pt_BR locale
 * ```
 */
fun Double.toCurrency(): String {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return format.format(this)
}

/**
 * Extension function to format a Float as currency.
 */
fun Float.toCurrency(): String {
    return this.toDouble().toCurrency()
}

/**
 * Extension function to format an Int as currency.
 */
fun Int.toCurrency(): String {
    return this.toDouble().toCurrency()
}

/**
 * Extension function to format a Long as currency.
 */
fun Long.toCurrency(): String {
    return this.toDouble().toCurrency()
}
