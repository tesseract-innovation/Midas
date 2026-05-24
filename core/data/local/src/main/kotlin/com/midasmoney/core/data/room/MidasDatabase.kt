package com.midasmoney.core.data.room

import com.midasmoney.core.util.Constants.DATABASE_NAME
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.midasmoney.core.data.room.converter.UUIDConverter
import com.midasmoney.core.data.room.converter.LocalDateConverter
import com.midasmoney.core.data.room.dao.AccountDao
import com.midasmoney.core.data.room.dao.GoalContributionDao
import com.midasmoney.core.data.room.dao.GoalDao
import com.midasmoney.core.data.room.dao.TransactionDao
import com.midasmoney.core.data.room.entity.AccountEntity
import com.midasmoney.core.data.room.entity.GoalContributionEntity
import com.midasmoney.core.data.room.entity.GoalEntity
import com.midasmoney.core.data.room.entity.TransactionEntity
import com.midasmoney.core.data.room.converter.InstantConverter

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class,
        GoalEntity::class,
        GoalContributionEntity::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    value = [
        UUIDConverter::class,
        InstantConverter::class,
        LocalDateConverter::class,
    ]
)
abstract class MidasDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun goalDao(): GoalDao
    abstract fun goalContributionDao(): GoalContributionDao

    companion object {
        @Volatile
        private var instance: MidasDatabase? = null

        fun getDatabase(context: Context): MidasDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MidasDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}
