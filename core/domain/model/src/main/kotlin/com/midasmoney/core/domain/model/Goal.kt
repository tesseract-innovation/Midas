package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.datetime.LocalDate

data class Goal(
    val title: String,
    val description: String,
    val amount: Double,
    val progress: Double,
    val icon: IconModel,
    val color: Int,
    val targetDate: LocalDate,
    val monthlyValue: Double,
    val id: UUID = UUID.randomUUID()
)
