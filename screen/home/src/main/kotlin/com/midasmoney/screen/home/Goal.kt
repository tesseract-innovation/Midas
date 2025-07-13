package com.midasmoney.screen.home

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.midasmoney.shared.ui.core.MidasColors
import java.util.UUID

data class Goal(
    val title: String,
    val description: String,
    val amount: Double,
    val progress: Double,
    val icon: ImageVector,
    val color: Color = MidasColors.Green.primary,
    val id: UUID = UUID.randomUUID()
)