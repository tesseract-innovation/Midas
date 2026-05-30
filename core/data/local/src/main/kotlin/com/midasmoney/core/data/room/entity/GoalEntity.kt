package com.midasmoney.core.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.midasmoney.core.util.Constants.DATABASE_ENTITY_GOAL

@Entity(tableName = DATABASE_ENTITY_GOAL)
data class GoalEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "progress")
    val progress: Double,
    @ColumnInfo(name = "icon")
    val icon: String,
    @ColumnInfo(name = "color")
    val color: Int,
    @ColumnInfo(name = "targetDate")
    val targetDate: String,
    @ColumnInfo(name = "monthlyValue")
    val monthlyValue: Double,
) : IEntity
