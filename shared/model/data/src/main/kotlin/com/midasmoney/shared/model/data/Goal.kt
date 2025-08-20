package com.midasmoney.shared.model.data

import java.time.LocalDate
import java.util.UUID

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