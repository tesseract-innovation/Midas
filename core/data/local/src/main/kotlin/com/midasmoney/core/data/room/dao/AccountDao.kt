package com.midasmoney.core.data.room.dao

import androidx.room.*
import com.midasmoney.core.data.room.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao : IDao<AccountEntity> {
    @Query("SELECT * FROM account")
    fun getAllAccounts(): Flow<List<AccountEntity>>
    
    @Query("SELECT * FROM account WHERE id = :id")
    suspend fun getAccountById(id: String): AccountEntity?
    
    @Query("SELECT * FROM account WHERE id = :id")
    fun getAccountByIdFlow(id: String): Flow<AccountEntity?>
    
    @Query("DELETE FROM account WHERE id = :id")
    suspend fun deleteAccountById(id: String)
    
    @Query("SELECT COUNT(*) FROM account")
    suspend fun getAccountCount(): Int
    
    @Query("SELECT SUM(balance) FROM account")
    suspend fun getTotalBalance(): Double?
}
