package com.midasmoney.domain.repository

import com.midasmoney.core.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface ITransactionRepository : IRepository<Transaction> {
    fun getTransactionForAccount(accountId: String): Flow<List<Transaction>>
    suspend fun getTotalAmountForAccount(accountId: String): Double
    suspend fun getTotalExpense(accountId: String): Double
    suspend fun getTotalIncome(accountId: String): Double
}
