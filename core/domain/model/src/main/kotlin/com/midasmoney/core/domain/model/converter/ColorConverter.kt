package com.midasmoney.core.domain.model.converter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object ColorConverter {
    fun colorToArgb(color: Color): Int = color.toArgb()

    fun aRgbToColor(aRgb: Int): Color = Color(aRgb)
}
