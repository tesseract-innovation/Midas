package com.midasmoney.core.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.midasmoney.core.util.Constants.DATABASE_ENTITY_ACCOUNT

@Entity(tableName = DATABASE_ENTITY_ACCOUNT)
data class AccountEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val icon: String,
    val color: Int,
    val balance: Double,
    val income: Double,
    val expense: Double
) : IEntity
