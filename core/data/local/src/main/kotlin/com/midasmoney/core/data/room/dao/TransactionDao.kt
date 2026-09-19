package com.midasmoney.core.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.midasmoney.core.data.room.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

data class AccountTransactionCount(
    val accountId: String,
    val count: Int,
)

@Dao
interface TransactionDao : IDao<TransactionEntity> {
    @Query("SELECT * FROM `transaction` ORDER BY createdAt DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Query("SELECT accountId, COUNT(*) AS count FROM `transaction` GROUP BY accountId")
    fun getTransactionCounts(): Flow<List<AccountTransactionCount>>

    @Query("SELECT * FROM `transaction` WHERE accountId = :accountId ORDER BY createdAt DESC")
    fun getTransactionsForAccount(accountId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM `transaction` WHERE id = :transactionId")
    suspend fun getTransactionById(transactionId: String): TransactionEntity?

    @Query("DELETE FROM `transaction` WHERE accountId = :accountId")
    suspend fun deleteTransactionsByAccountId(accountId: String)

    @Query("SELECT SUM(amount) FROM `transaction` WHERE accountId = :accountId")
    fun getTotalAmountForAccount(accountId: String): Double

    // Balance-level types (INITIAL_BALANCE, BALANCE_ADJUSTMENT) aren't in the fixed
    // expense-type list below since they can go either way - a negative one is an
    // adjustment down and belongs in the expense total, not the income total.
    @Query(
        """
        SELECT SUM(
            CASE
                WHEN type IN ('WITHDRAWAL', 'FEES', 'REFUND', 'LOAN_PAYMENT', 'INTEREST', 'TAX', 'EXPENSE') THEN 0
                WHEN type IN ('INITIAL_BALANCE', 'BALANCE_ADJUSTMENT') THEN MAX(amount, 0)
                ELSE amount
            END
        ) FROM `transaction` WHERE accountId = :accountId
        """,
    )
    fun getTotalIncomeForAccount(accountId: String): Double

    @Query(
        """
        SELECT SUM(
            CASE
                WHEN type IN ('WITHDRAWAL', 'FEES', 'REFUND', 'LOAN_PAYMENT', 'INTEREST', 'TAX', 'EXPENSE') THEN ABS(amount)
                WHEN type IN ('INITIAL_BALANCE', 'BALANCE_ADJUSTMENT') THEN ABS(MIN(amount, 0))
                ELSE 0
            END
        ) FROM `transaction` WHERE accountId = :accountId
        """,
    )
    fun getTotalExpenseForAccount(accountId: String): Double
}
