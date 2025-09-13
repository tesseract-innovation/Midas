package com.midasmoney.core.ui.values

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun Float.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}

fun BigDecimal.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}

fun Double.toCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}