package com.midasmoney.shared.ui.core.values

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.midasmoney.shared.model.data.TransactionHistoryItem
import com.midasmoney.shared.model.data.TransactionType
import com.midasmoney.shared.ui.core.color.ColorConverter
import com.midasmoney.shared.ui.core.color.MidasColors
import com.midasmoney.shared.ui.core.date.DateTimeUtils
import com.midasmoney.shared.ui.core.icon.IconMapper

fun TransactionHistoryItem.formatAmount(): String {
    return "${if (this.type == TransactionType.EXPENSE) "-" else "+"}${this.amount.toCurrency()}"
}

fun TransactionHistoryItem.formatAmountColor(): Color {
    return if (this.type == TransactionType.EXPENSE) MidasColors.Red.primary else MidasColors.Green.primary
}

fun TransactionHistoryItem.formatDate(): String {
    return DateTimeUtils.formatDate(this.date)
}

fun TransactionHistoryItem.formatIcon(): ImageVector {
    return IconMapper.getImageVector(this.category.icon)
}

fun TransactionHistoryItem.formatIconColor(): Color {
    return  ColorConverter.aRgbToColor(this.category.color)
}

fun TransactionHistoryItem.formatIconColorBackground(): Color {
    return ColorConverter.aRgbToColor(this.category.color).copy(alpha = 0.2f)
}