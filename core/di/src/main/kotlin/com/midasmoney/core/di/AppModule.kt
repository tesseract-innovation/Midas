package com.midasmoney.core.di

import android.content.Context
import com.midasmoney.core.data.room.MidasDatabase
import com.midasmoney.core.data.room.dao.AccountDao
import com.midasmoney.core.data.room.dao.TransactionDao
import com.midasmoney.core.data.room.repository.account.AccountRepository
import com.midasmoney.core.data.room.repository.transaction.TransactionRepository
import com.midasmoney.domain.repository.IAccountRepository
import com.midasmoney.domain.repository.ITransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): MidasDatabase {
        return MidasDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(
        db: MidasDatabase
    ): AccountDao {
        return db.accountDao()
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        accountDao: AccountDao
    ): IAccountRepository {
        return AccountRepository(accountDao)
    }

    @Provides
    @Singleton
    fun provideTransactionDao(
        db: MidasDatabase
    ): TransactionDao {
        return db.transactionDao()
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(
        transactionDao: TransactionDao
    ): ITransactionRepository {
        return TransactionRepository(transactionDao)
    }
}
