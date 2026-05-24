package com.midasmoney.core.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.midasmoney.core.data.room.entity.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao : IDao<GoalEntity> {
    @Query("SELECT * FROM goal")
    fun getAllGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM goal WHERE id = :id")
    suspend fun getGoalById(id: String): GoalEntity?

    @Query("SELECT * FROM goal WHERE id = :id")
    fun observeGoalById(id: String): Flow<GoalEntity?>

    @Query("DELETE FROM goal WHERE id = :id")
    suspend fun deleteGoalById(id: String)
}
