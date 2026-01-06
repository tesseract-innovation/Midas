package com.midasmoney.core.data.room.converter

import androidx.room.TypeConverter
import com.midasmoney.core.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(value: String?): UUID? {
        return value?.let { UUID(it) }
    }
}
