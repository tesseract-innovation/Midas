package com.midasmoney.core.domain.model

import com.midasmoney.core.util.UUID
import kotlinx.serialization.Serializable

@Serializable
enum class AccountType(val displayName: String) {
    CHECKING("Checking"),
    SAVINGS("Savings"),
    CREDIT_CARD("Credit Card"),
    INVESTMENT("Investments"),
    WALLET("Wallet"),
}

@Serializable
data class Account(
    val name: String,
    val icon: IconModel,
    val color: Int,
    var balance: Balance,
    val transactions: List<Transaction>,
    val type: AccountType = AccountType.CHECKING,
    // Credit-card-only fields; null for every other account type.
    val creditLimit: Double? = null,
    val dueDay: Int? = null,
    val id: UUID = UUID.randomUUID(),
    val isActive: Boolean = true,
)
