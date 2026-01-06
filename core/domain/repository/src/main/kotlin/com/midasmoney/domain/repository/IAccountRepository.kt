package com.midasmoney.domain.repository

import com.midasmoney.core.domain.model.Account
import kotlinx.coroutines.flow.Flow

interface IAccountRepository : IRepository<Account> {
    val allAccounts: Flow<List<Account>>
    fun getAccountByIdFlow(accountId: String): Flow<Account?>
    suspend fun deleteAccountById(accountId: String): Result<Unit>
    suspend fun getAccountCount(): Int
    suspend fun getTotalBalance(): Double
}
