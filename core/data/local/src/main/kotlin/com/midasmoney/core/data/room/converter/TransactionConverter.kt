package com.midasmoney.core.data.room.converter

import androidx.room.TypeConverter
import com.midasmoney.core.domain.model.TransactionType

class TransactionConverter {
    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}
