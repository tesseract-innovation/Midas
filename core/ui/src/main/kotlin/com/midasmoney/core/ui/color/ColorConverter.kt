package com.midasmoney.core.ui.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object ColorConverter {
    fun colorToArbg(color: Color): Int = color.toArgb()

    fun aRgbToColor(aRgb: Int): Color = Color(aRgb)
}