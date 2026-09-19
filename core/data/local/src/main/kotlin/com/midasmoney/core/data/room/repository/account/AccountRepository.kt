package com.midasmoney.core.data.room.repository.account

import com.midasmoney.core.data.room.dao.AccountDao
import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.dao.TransactionDao
import com.midasmoney.core.data.room.entity.AccountEntity
import com.midasmoney.core.data.room.repository.BaseRepository
import com.midasmoney.core.data.room.repository.transaction.TransactionEntityMapper
import com.midasmoney.core.domain.model.Account
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepository
    @Inject
    constructor(
        private val accountDao: AccountDao,
        private val transactionDao: TransactionDao,
    ) : BaseRepository<Account, AccountEntity>(), IAccountRepository {
        override val dao: IDao<AccountEntity>
            get() = accountDao
        override val entityMapper: IEntityMapper<AccountEntity, Account>
            get() = AccountEntityMapper
        // The list screen only needs a per-account transaction count, not the full
        // rows, so it's driven off a lightweight SQL COUNT rather than fetching
        // (and re-fetching, on every write anywhere in the transaction table) every
        // transaction in the app just to group and count them in memory.
        override val allAccounts: Flow<List<Account>> =
            combine(
                accountDao.getAllAccounts(),
                transactionDao.getTransactionCounts(),
            ) { accountEntities, counts ->
                val countByAccountId = counts.associate { it.accountId to it.count }
                accountEntities.map { accountEntity ->
                    AccountEntityMapper.toDomain(accountEntity).copy(
                        transactionCount = countByAccountId[accountEntity.id] ?: 0,
                    )
                }
            }

        override suspend fun getById(id: String): Account? {
            val accountEntity = accountDao.getAccountById(id) ?: return null
            val transactions = transactionDao.getTransactionsForAccount(id).first()
            return AccountEntityMapper.toDomain(accountEntity).copy(
                transactions = transactions.map { TransactionEntityMapper.toDomain(it) },
                transactionCount = transactions.size,
            )
        }

        override suspend fun getAll(): Flow<List<Account>> {
            return allAccounts
        }

        override fun getAccountByIdFlow(accountId: String): Flow<Account?> {
            return combine(
                accountDao.getAccountByIdFlow(accountId),
                transactionDao.getTransactionsForAccount(accountId),
            ) { accountEntity, transactionEntities ->
                accountEntity?.let {
                    AccountEntityMapper.toDomain(it).copy(
                        transactions = transactionEntities.map { te -> TransactionEntityMapper.toDomain(te) },
                        transactionCount = transactionEntities.size,
                    )
                }
            }
        }

        override suspend fun deleteAccountById(accountId: String): Result<Unit> {
            return try {
                accountDao.deleteAccountById(accountId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getAccountCount(): Int {
            return accountDao.getAccountCount()
        }

        override suspend fun getTotalBalance(): Double {
            return accountDao.getTotalBalance() ?: 0.0
        }
    }
