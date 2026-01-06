package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    var totalValue: Double,
    var income: Double,
    var expense: Double,
    val id: UUID = UUID.randomUUID()
)
