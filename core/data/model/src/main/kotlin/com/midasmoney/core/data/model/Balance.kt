package com.midasmoney.core.data.model

import java.util.UUID

data class Balance(
    val totalValue: Float,
    val incomeValue: Float,
    val expenseValue: Float,
    val id: UUID = UUID.randomUUID()
)