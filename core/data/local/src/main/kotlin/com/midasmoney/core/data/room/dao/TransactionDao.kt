package com.midasmoney.core.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.midasmoney.core.data.room.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao : IDao<TransactionEntity> {
    @Query("SELECT * FROM `transaction` ORDER BY createdAt DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE accountId = :accountId ORDER BY createdAt DESC")
    fun getTransactionsForAccount(accountId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: String): TransactionEntity?

    @Query("DELETE FROM `transaction` WHERE accountId = :accountId")
    suspend fun deleteTransactionsByAccountId(accountId: String)

    @Query("SELECT SUM(amount) FROM `transaction` WHERE accountId = :accountId")
    fun getTotalAmountForAccount(accountId: String): Double

    @Query("SELECT SUM(amount) FROM `transaction` WHERE accountId = :accountId AND type IN ('WITHDRAWAL', 'FEES', 'REFUND', 'LOAN_PAYMENT', 'INTEREST', 'TAX', 'EXPENSE')")
    fun getTotalIncomeForAccount(accountId: String): Double

    @Query("SELECT SUM(amount) FROM `transaction` WHERE accountId = :accountId AND type NOT IN ('WITHDRAWAL', 'FEES', 'REFUND', 'LOAN_PAYMENT', 'INTEREST', 'TAX', 'EXPENSE')")
    fun getTotalExpenseForAccount(accountId: String): Double
}
