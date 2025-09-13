package com.midasmoney.core.ui.values

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.midasmoney.core.data.model.Transaction
import com.midasmoney.core.data.model.TransactionType
import com.midasmoney.core.ui.color.ColorConverter
import com.midasmoney.core.ui.color.MidasColors
import com.midasmoney.core.ui.date.DateTimeUtils
import com.midasmoney.core.ui.icon.IconMapper

fun Transaction.formatAmount(): String {
    return "${if (this.type == TransactionType.EXPENSE) "-" else "+"}${this.amount.toCurrency()}"
}

fun Transaction.formatAmountColor(): Color {
    return if (this.type == TransactionType.EXPENSE) MidasColors.Red.primary else MidasColors.Green.primary
}

fun Transaction.formatDate(): String {
    return DateTimeUtils.formatDate(this.date)
}

fun Transaction.formatIcon(): ImageVector {
    return IconMapper.getImageVector(this.category.icon)
}

fun Transaction.formatIconColor(): Color {
    return  ColorConverter.aRgbToColor(this.category.color)
}

fun Transaction.formatIconColorBackground(): Color {
    return ColorConverter.aRgbToColor(this.category.color).copy(alpha = 0.2f)
}