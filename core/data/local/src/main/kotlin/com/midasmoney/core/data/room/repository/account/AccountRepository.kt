package com.midasmoney.core.data.room.repository.account

import com.midasmoney.core.data.room.dao.AccountDao
import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.entity.AccountEntity
import com.midasmoney.core.data.room.repository.BaseRepository
import com.midasmoney.core.domain.model.Account
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val accountDao: AccountDao
) : BaseRepository<Account, AccountEntity>(), IAccountRepository {
    override val dao: IDao<AccountEntity>
        get() = accountDao
    override val entityMapper: IEntityMapper<AccountEntity, Account>
        get() = AccountEntityMapper
    override val allAccounts: Flow<List<Account>> = accountDao.getAllAccounts()
        .map { entities -> entities.map { it.let { AccountEntityMapper.toDomain(it) } } }

    override suspend fun getById(id: String): Account? {
        val accountEntity = accountDao.getAccountById(id)
        return accountEntity?.let { AccountEntityMapper.toDomain(it) }
    }

    override suspend fun getAll(): Flow<List<Account>> {
        return allAccounts
    }

    override fun getAccountByIdFlow(accountId: String): Flow<Account?> {
        return accountDao.getAccountByIdFlow(accountId)
            .map { it?.let { AccountEntityMapper.toDomain(it) } }
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
