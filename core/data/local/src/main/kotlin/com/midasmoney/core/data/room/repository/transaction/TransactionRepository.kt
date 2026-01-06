package com.midasmoney.core.data.room.repository.transaction

import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.dao.TransactionDao
import com.midasmoney.core.data.room.entity.TransactionEntity
import com.midasmoney.core.data.room.repository.BaseRepository
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.domain.repository.ITransactionRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
) : BaseRepository<Transaction, TransactionEntity>(), ITransactionRepository {
    override val dao: IDao<TransactionEntity>
        get() = transactionDao
    override val entityMapper: IEntityMapper<TransactionEntity, Transaction>
        get() = TransactionEntityMapper

    override suspend fun getById(id: String): Transaction? {
        val transactionEntity = transactionDao.getTransactionById(id)
        return transactionEntity?.let { TransactionEntityMapper.toDomain(transactionEntity) }
    }

    override suspend fun getAll(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
            .map { it.map { entity -> TransactionEntityMapper.toDomain(entity) } }
    }

    override fun getTransactionForAccount(accountId: String): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForAccount(accountId).map { it ->
            it.map { entity -> entityMapper.toDomain(entity) }
        }
    }

    override suspend fun getTotalAmountForAccount(accountId: String): Double {
        return transactionDao.getTotalAmountForAccount(accountId)
    }

    override suspend fun getTotalExpense(accountId: String): Double {
        return transactionDao.getTotalExpenseForAccount(accountId)
    }

    override suspend fun getTotalIncome(accountId: String): Double {
        return transactionDao.getTotalIncomeForAccount(accountId)
    }
}
