package com.midasmoney.shared.model.data

import java.util.UUID

data class Balance(
    val totalValue: Float,
    val incomeValue: Float,
    val expenseValue: Float,
    val id: UUID = UUID.randomUUID()
)