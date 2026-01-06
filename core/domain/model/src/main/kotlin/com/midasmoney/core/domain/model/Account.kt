package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val name: String,
    val icon: IconModel,
    val color: Int,
    var balance: Balance,
    val transactions: List<Transaction>,
    val id: UUID = UUID.randomUUID()
)
